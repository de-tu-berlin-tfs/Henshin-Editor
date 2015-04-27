/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.muvitor.clipboard;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList.Generic;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.clipboard.core.AbstractClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteAction;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteTarget;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.IClipboardSupport2;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.LoadingEMFResource;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ObjectCopyType;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.PasteIntoParentOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.PasteOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ResourceInfoProcessor;

import de.tub.tfs.muvitor.actions.GenericPasteAction;
import de.tub.tfs.muvitor.actions.GenericPasteAction.IPasteRule;

public class MuvitorClipboardSupport extends AbstractClipboardSupport implements IClipboardSupport2 {

	private final class LoadingEMFResourceExtension extends LoadingEMFResource {
		private PasteIntoParentOperation op;

		private LoadingEMFResourceExtension(ResourceSet rset, String encoding,
				Map defaultLoadOptions,
				IClipboardSupport clipboardOperationHelper,PasteIntoParentOperation op) {
			super(rset, encoding, defaultLoadOptions, clipboardOperationHelper);
			this.op = op;
		}
		
		@Override
		protected void doUnload() {
			for (Object pasted : op.getPastedElementSet()) {
				if (pasted instanceof EObject){
					EObject p = (EObject) pasted;
					EObject container = p.eContainer();
					EStructuralFeature cont = ((EObject) pasted).eContainingFeature();
					this.getContents().remove(pasted);
					Object c = container.eGet(cont);
					if (cont.isMany()){
						((List)c).add(p);
					} else {
						container.eSet(cont, p);
					}
				}
			}
			// TODO Auto-generated method stub
			super.doUnload();
		}
	}


	public MuvitorClipboardSupport() {
		super();
	}


	protected EAttribute getNameAttribute(EClass eClass) {
		
		return eClass.getEIDAttribute();
	}
	
	public PasteAction getPasteCollisionAction(EClass eClass) {
		return super.getPasteCollisionAction(eClass);
	}
	
	public boolean isCopyAlways(EObject context, EReference eReference,
			Object value) {
			return super.isCopyAlways(context, eReference, value);
	}
	
	
	@Override
	public boolean shouldOverrideChildPasteOperation(EObject parentElement,
			EObject childEObject) {
		
		return true;
	}
	
	
	@Override
	public OverridePasteChildOperation getOverrideChildPasteOperation(
			final PasteChildOperation overriddenChildPasteOperation) {
		// TODO Auto-generated method stub
		return new OverridePasteChildOperation(overriddenChildPasteOperation) {
			
			private Map embeddedCopyParentObjectInfoMap = new HashMap();
			/**
			 * Copy from org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation
			 * @return
			 * @throws Exception
			 */
			private ObjectInfo makeEmbeddedCopyParentObjectInfo(
					EObject embeddedCopyParent) {
					ObjectInfo objectInfo = (ObjectInfo) embeddedCopyParentObjectInfoMap
						.get(embeddedCopyParent);
					if (objectInfo == null) {
						objectInfo = new ObjectInfo();
						objectInfo.objCopyType = ObjectCopyType.OBJ_COPY_TYPE_PARENT;
						objectInfo.objId = getLoadedEObjectID(embeddedCopyParent);
						objectInfo.containerId = getLoadedEObjectID(embeddedCopyParent
							.eContainer());
						objectInfo.containerClass = embeddedCopyParent.eContainer()
							.eClass().getInstanceClassName();
						if (objectInfo.objId.equals(getChildObjectInfo().copyParentId) == false) {
							objectInfo.copyParentId = getChildObjectInfo().copyParentId;
						} else {
							objectInfo.copyParentId = ResourceInfoProcessor.NONE;
						}
						objectInfo.hints = ResourceInfoProcessor.NONE;
						//cache it
						embeddedCopyParentObjectInfoMap.put(embeddedCopyParent, objectInfo);
					}

					return objectInfo;
				}
			/**
			 * Copy from org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation
			 * @return
			 * @throws Exception
			 */
			private EObject doPasteIntoCopyParent(ObjectInfo theCopyParentObjectInfo)
					throws Exception {
					PasteChildOperation copyParentProcess = getAuxiliaryChildPasteProcess(theCopyParentObjectInfo);
					copyParentProcess.paste();
					EObject pastedCopyParent = copyParentProcess.getPastedElement();
					if (pastedCopyParent != null) {
						//the direct copy parent should have been pasted correctly by now
						return doPasteInto(getPastedDirectCopyParent());
					}
					return null;
				}
			/**
			 * Copy from org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation
			 * @return
			 * @throws Exception
			 */
			private EObject doPasteIntoNearestCopyParent(
					EObject topMostCopyParentEObject) throws Exception {
					EObject nearestParent = getLoadedEObject(getChildObjectInfo().containerId);
					while (nearestParent.equals(topMostCopyParentEObject) == false) {
						EObject parentElement = doPasteIntoCopyParent(makeEmbeddedCopyParentObjectInfo(nearestParent));
						if (parentElement != null) {
							return parentElement;
						}
						nearestParent = nearestParent.eContainer();
					}

					return null;
				}
			/**
			 * Copy from org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation
			 * @return
			 * @throws Exception
			 */
			private EObject doPasteIntoCopyParent() throws Exception {

				//check if copyParentEObject exists in the target model already
				//try matching direct copy parent ID.
				EObject existingCopyParentEObject = getEObject(getChildObjectInfo().containerId);
				if (existingCopyParentEObject != null) {
					return doPasteInto(existingCopyParentEObject);
				}

				//check if the copy-parent has been
				//pasted already by a sibling paste operation that executed before us?
				EObject pastedDirectCopyParent = getPastedDirectCopyParent();
				if (pastedDirectCopyParent != null) {
					//the direct copy parent should have been pasted correctly already
					return doPasteInto(pastedDirectCopyParent);
				}

				EObject nearestParent = null;
				if (isCopyParentDirectParent() == false) {
					nearestParent = getLoadedEObject(getChildObjectInfo().containerId);
					EObject perent = nearestParent.eContainer();
					EObject root = getCopyParentEObject();
					while ((perent != null) && (perent.equals(root) == false)) {
						existingCopyParentEObject = getPastedEObject(perent);
						if (existingCopyParentEObject != null) {
							break;
						}
						nearestParent = perent;
						perent = nearestParent.eContainer();
					}
					if (existingCopyParentEObject == null) {
						//check the root itself
						existingCopyParentEObject = getPastedEObject(root);
					}
				}

				if (existingCopyParentEObject != null) {
					//the nearestParent copy parent should have been pasted correctly
					// already
					//paste the nearest-parent itself first, the paste the child into
					// it afterwards
					return doPasteIntoCopyParent(makeEmbeddedCopyParentObjectInfo(nearestParent));
				} else {
					//no parent with same ID, and the copy-parent not pasted already,
					//then try other ways to match a parent
					PasteTarget possibleParent = getSuitableParentUsingAncestry(getLoadedDirectContainerEObject()
						.eClass().getInstanceClassName());
					if (possibleParent != null) {
						return doPasteInto(possibleParent);
					} else {
						//no suitable exisiting parent then the copy-parent itself
						//needs to be pasted first
						EObject element = doPasteIntoNearestCopyParent(getCopyParentEObject());
						if (element != null) {
							//found a nearest copy parent and pasted it successfully
							return element;
						}
						//now final try: use the root copy Parent?
						return doPasteIntoCopyParent(getCopyParentObjectInfo());
					}
				}
			}
			
			@Override
			public void paste() throws Exception {
				if (hasCopyParent()) {
					setPastedElement(doPasteIntoCopyParent());
				} else {
					EObject element = null;
					//either it is not a copyAlways, or it is a copyAlways
					// whose
					//original parent didn't resolve, thus, proceed normally
					//by trying to paste in target obj
					element = doPasteInto(getParentTarget());
					
					if (element == null) {
						/*-------------
						 //failed to copy in target parent...then check if it is a copy-always and its
						 // original parent resolves in target model
						 if (isCopyAlways()) {
						 EObject resolvedCopyAlwaysParent = getEObject(getChildObjectInfo().containerId);
						 if (resolvedCopyAlwaysParent != null) {
						 //found original parent for this copyAlways object,
						 // then use it,
						 //instead of user selected parent
						 element = doPasteInto(resolvedCopyAlwaysParent);
						 }
						 }
						 -------------*/
						if ((element == null)
							&& ((getChildObjectInfo()
								.hasHint(ClipboardUtil.PASTE_TO_TARGET_PARENT)) || (isCopyAlways()))) {
							PasteTarget possibleParent = getSuitableParentUsingAncestry(getChildObjectInfo().containerClass);
							if (possibleParent != null) {
								element = doPasteInto(possibleParent);
							}
						}
					}
					setPastedElement(element);
				}

				//did we succeed?
				if (getPastedElement() != null) {
					addPastedElement(getPastedElement());
				} else {
					addPasteFailuresObject(getEObject());
				}
			}
			
			@Override
			public PasteChildOperation getPostPasteOperation() {
				// TODO Auto-generated method stub
				return new PostPasteChildOperation(overriddenChildPasteOperation,Arrays.asList(new PasteChildOperation(getParentPasteProcess(), getChildObjectInfo()){
					
					
					@Override
					public void paste() throws Exception {
						super.paste();
						performPostPasteProcessing(getPastedElement(),getParentEObject());
						
					}
								
					
					@Override
					public PasteChildOperation getPostPasteOperation() {
				        
				       return null;
				        
					}
					
				}) );
			}
		};
	}
	
	private void performPostPasteProcessing(EObject copy,
			EObject pasteIntoEObject) {
		if (copy == null)
			return;
		final LinkedList<EClass> copySuperTypes = new LinkedList<EClass>( copy.eClass().getEAllSuperTypes());
		copySuperTypes.add(EcorePackage.Literals.EOBJECT);
		copySuperTypes.add(copy.eClass());
		
		for (final Entry<EClass, IPasteRule> entry : GenericPasteAction.pasteRules.entrySet()) {
			
			if (copySuperTypes.contains(entry.getKey())) {
				entry.getValue().afterPaste(copy, copy.eContainer());
			}
		}	
	}
	@Override
	public void performPostPasteProcessing(Set pastedEObjects) {
		Set<EObject> pasted = pastedEObjects;
		for (final EObject copy : pasted) {
			//performPostPasteProcessing(copy, copy.eContainer());
		}
	}


	@Override
	public boolean shouldOverridePasteIntoParentOperation(
			PasteTarget pasteTarget, Map hintsMap) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public PasteIntoParentOperation getPasteIntoParentOperation(
			PasteOperation pasteOperation, PasteTarget pasteTarget, Map hintsMap)
			throws Exception {
		// TODO Auto-generated method stub
		return new PasteIntoParentOperation(pasteOperation,
				pasteTarget, hintsMap){
			@Override
			protected LoadingEMFResource loadEObjects()
					throws Exception {
					ByteArrayInputStream inputStream = new ByteArrayInputStream(
						getResourceInfo().data.getBytes(getResourceInfo().encoding));
					LoadingEMFResource resource = new LoadingEMFResourceExtension(getParentResource().getResourceSet(), getResourceInfo().encoding,
							getLoadOptionsMap(), getClipboardOperationHelper(),this);
					
					resource.load(inputStream, null);
					return resource;
			}
		};
		
	}
	
}