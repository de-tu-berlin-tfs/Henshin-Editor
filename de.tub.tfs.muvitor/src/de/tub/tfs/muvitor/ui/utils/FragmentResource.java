package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;

import de.tub.tfs.muvitor.ui.IDUtil;

public class FragmentResource extends XMIResourceImpl {

	private EMFModelManager manager;


	public FragmentResource(URI uri,EMFModelManager manager) {
		super(uri);
		this.manager = manager;
	}
	
	@Override
	protected XMLHelper createXMLHelper() {
		// TODO Auto-generated method stub
		return new XMIHelperImpl(this){
			@Override
			public String getID(EObject obj) {
				if (!useUUIDs())
					return super.getID(obj);
				String id = IDUtil.getIDForModel(obj);
				return id;
			}
			
		};
	}

	protected BasicEList<EObject> contents;


	@Override
	public EList<EObject> getContents()
	{
		if (contents == null)
		{
			contents = new BasicInternalEList<EObject>(EObject.class);
		}
		return contents;
	}
	
	@Override
	protected boolean useUUIDs() {
		return true;
	}
	
	public void cleanUp() {
		getContents().clear();
		this.unload();
		
	}
	@Override
	protected XMLSave createXMLSave() {
		return new XMISaveImpl(createXMLHelper()){

			private boolean checkForDelegates(EObject o,EStructuralFeature f){
				if (manager.replacedClasses.contains(o.eClass()) && o.eClass().getEStructuralFeatures().contains(f)){
					
					boolean result = manager.saveDelegates.get(o.eClass()).shouldSkipSave(o, f);
															
							
					return result;
				}
				return false;
			}


			@Override
			protected boolean saveFeatures(EObject o, boolean attributesOnly)
			{
				EClass eClass = o.eClass();   
			
				int contentKind = extendedMetaData == null ? ExtendedMetaData.UNSPECIFIED_CONTENT : extendedMetaData.getContentKind(eClass);     
				if (!toDOM)
				{
					switch (contentKind)
					{
					case ExtendedMetaData.MIXED_CONTENT:
					case ExtendedMetaData.SIMPLE_CONTENT: 
					{
						doc.setMixed(true);
						break;
					}
					}
				}

				if (o == root)
				{
					writeTopAttributes(root);
				}

				EStructuralFeature[] features = featureTable.getFeatures(eClass);
				int[] featureKinds = featureTable.getKinds(eClass, features);
				int[] elementFeatures = null;
				int elementCount = 0;

				String content = null;

				// Process XML attributes
				LOOP:
					for (int i = 0; i < features.length; i++ )
					{
						int kind = featureKinds[i];
						EStructuralFeature f = features[i];

						if (!checkForDelegates(o,features[i])){
							
							continue;
						}
							

						if (kind != TRANSIENT && shouldSaveFeature(o, f))
						{
							switch (kind)
							{
							case DATATYPE_ELEMENT_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getDataTypeElementSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case DATATYPE_SINGLE:
							{
								saveDataTypeSingle(o, f);
								continue LOOP;
							}
							case DATATYPE_SINGLE_NILLABLE:
							{
								if (!isNil(o, f))
								{
									saveDataTypeSingle(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ATTRIBUTE_SINGLE:
							{
								saveEObjectSingle(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_MANY:
							{
								saveEObjectMany(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_IDREF_SINGLE:
							{
								saveIDRefSingle(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_IDREF_MANY:
							{
								saveIDRefMany(o, f);
								continue LOOP;
							}
							case OBJECT_HREF_SINGLE_UNSETTABLE:
							{
								if (isNil(o, f))
								{
									break;
								}
								// it's intentional to keep going
							}
							case OBJECT_HREF_SINGLE:
							{
								if (useEncodedAttributeStyle)
								{
									saveEObjectSingle(o, f);
									continue LOOP;
								}
								else
								{
									switch (sameDocSingle(o, f))
									{
									case SAME_DOC:
									{
										saveIDRefSingle(o, f);
										continue LOOP;
									}
									case CROSS_DOC:
									{
										break;
									}
									default:
									{
										continue LOOP;
									}
									}
								}
								break;
							}
							case OBJECT_HREF_MANY_UNSETTABLE:
							{
								if (isEmpty(o, f))
								{
									saveManyEmpty(o, f);
									continue LOOP;
								}
								// It's intentional to keep going.
							}
							case OBJECT_HREF_MANY:
							{
								if (useEncodedAttributeStyle)
								{
									saveEObjectMany(o, f);
									continue LOOP;
								}
								else
								{
									switch (sameDocMany(o, f))
									{
									case SAME_DOC:
									{
										saveIDRefMany(o, f);
										continue LOOP;
									}
									case CROSS_DOC:
									{
										break;
									}
									default:
									{
										continue LOOP;
									}
									}
								}
								break;
							}
							case OBJECT_ELEMENT_SINGLE_UNSETTABLE:
							case OBJECT_ELEMENT_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementReferenceSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_MANY:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementReferenceManySimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE:
							case OBJECT_ELEMENT_IDREF_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementIDRefSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_IDREF_MANY:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementIDRefManySimple(o, f);
									continue LOOP;
								}
								break;
							}
							case DATATYPE_ATTRIBUTE_MANY:
							{
								break;
							}
							case OBJECT_CONTAIN_MANY_UNSETTABLE:
							case DATATYPE_MANY:
							{
								if (isEmpty(o, f))
								{
									saveManyEmpty(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_CONTAIN_SINGLE_UNSETTABLE:
							case OBJECT_CONTAIN_SINGLE:
							case OBJECT_CONTAIN_MANY:
							case ELEMENT_FEATURE_MAP:
							{
								break;
							}
							case ATTRIBUTE_FEATURE_MAP:
							{
								saveAttributeFeatureMap(o, f);
								continue LOOP;
							}
							default:
							{
								continue LOOP;
							}
							}

							if (attributesOnly)
							{
								continue LOOP;
							}

							// We only get here if we should do this.
							//
							if (elementFeatures == null)
							{
								elementFeatures = new int[features.length];
							}
							elementFeatures[elementCount++] = i;
						}
					}

				processAttributeExtensions(o);

				if (elementFeatures == null)
				{
					if (content == null)
					{
						content = getContent(o, features);
					}

					if (content == null)
					{
						if (o == root && writeTopElements(root))
						{
							endSaveFeatures(o, 0, null);
							return true;
						}
						else
						{
							endSaveFeatures(o, EMPTY_ELEMENT, null);
							return false;
						}
					}
					else
					{
						endSaveFeatures(o, CONTENT_ELEMENT, content);
						return true;
					}
				}

				if (o == root)
				{
					writeTopElements(root);
				}

				// Process XML elements
				for (int i = 0; i < elementCount; i++ )
				{
					int kind = featureKinds[elementFeatures[i]];
					EStructuralFeature f = features[elementFeatures[i]];
					
					if (!checkForDelegates(o,f))
						continue;
					
					switch (kind)
					{
					case DATATYPE_SINGLE_NILLABLE:
					{
						saveNil(o, f);
						break;
					}
					case ELEMENT_FEATURE_MAP:
					{
						saveElementFeatureMap(o, f);
						break;
					}
					case DATATYPE_MANY:
					{
						saveDataTypeMany(o, f);
						break;
					}
					case DATATYPE_ATTRIBUTE_MANY:
					{
						saveDataTypeAttributeMany(o, f);
						break;
					}
					case DATATYPE_ELEMENT_SINGLE:
					{
						saveDataTypeElementSingle(o, f);
						break;
					}
					case OBJECT_CONTAIN_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_CONTAIN_SINGLE:
					{
						saveContainedSingle(o, f);
						break;
					}
					case OBJECT_CONTAIN_MANY_UNSETTABLE:
					case OBJECT_CONTAIN_MANY:
					{
						saveContainedMany(o, f);
						break;
					}
					case OBJECT_HREF_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_HREF_SINGLE:
					{
						saveHRefSingle(o, f);
						break;
					}
					case OBJECT_HREF_MANY_UNSETTABLE:
					case OBJECT_HREF_MANY:
					{
						saveHRefMany(o, f);
						break;
					}
					case OBJECT_ELEMENT_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_ELEMENT_SINGLE:
					{
						saveElementReferenceSingle(o, f);
						break;
					}
					case OBJECT_ELEMENT_MANY:
					{
						saveElementReferenceMany(o, f);
						break;
					}
					case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_ELEMENT_IDREF_SINGLE:
					{
						saveElementIDRefSingle(o, f);
						break;
					}
					case OBJECT_ELEMENT_IDREF_MANY:
					{
						saveElementIDRefMany(o, f);
						break;
					}
					}
				}
				endSaveFeatures(o, 0, null);
				return true;
			}
		};
	}
	
}
