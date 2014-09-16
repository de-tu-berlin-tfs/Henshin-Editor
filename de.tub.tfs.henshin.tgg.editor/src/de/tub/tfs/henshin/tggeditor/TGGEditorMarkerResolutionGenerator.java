/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.type.ErrorType;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class TGGEditorMarkerResolutionGenerator implements IMarkerResolutionGenerator{

	public static class TGGMarkerAttributes {
		public static final String errorType = "de.tub.tfs.tgg.editor.marker.type";
		public static final String errorObject = "de.tub.tfs.tgg.editor.marker.object";
	}
	
	public static HashMap<String,Node> nodes = new HashMap<String,Node>();
	
	/*
	 * 
	 * "MissingMarker"
	 * "NodeDeleted"
	 * "EdgeDeleted"
	 */
	public enum ErrorTypes {
		MissingMarker(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
	
				return new IMarkerResolution[]{
						new IMarkerResolution() {
							
							@Override
							public void run(IMarker marker) {
			
								try {
									String source_ID = (String) marker.getAttribute(IMarker.SOURCE_ID);
									EObject n = IDUtil.getModelForID(source_ID);
									if (n == null || !(EcoreUtil.getRootContainer(n) instanceof Module)){
										marker.delete();
										return;
									}
									if (n instanceof TNode){
										((TNode)n).setMarkerType(RuleUtil.NEW);
									}
									if (n instanceof TEdge){
										
										((TEdge)n).setMarkerType(RuleUtil.NEW);
									}
									if (n instanceof TAttribute){
										
										((TAttribute)n).setMarkerType(RuleUtil.NEW);
									}
									
									marker.delete();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
							
							@Override
							public String getLabel() {
								// TODO Auto-generated method stub
								return "Add the missing marker.";
							}
						}
				};
			}
		},
		NodeDeleted(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
				// TODO Auto-generated method stub
				return new IMarkerResolution[]{
						new IMarkerResolution() {
							
							@Override
							public void run(IMarker marker) {
								
								Node n;
								try {
									String source_ID = (String) marker.getAttribute(IMarker.SOURCE_ID);
									n = (Node) IDUtil.getModelForID(source_ID);
									if (n == null || !(EcoreUtil.getRootContainer(n) instanceof Module)){
										marker.delete();
										return;
									}
									SimpleDeleteEObjectCommand c = new SimpleDeleteEObjectCommand(n);
									
									c.execute();
									
									//marker.delete();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
							
							@Override
							public String getLabel() {
								// TODO Auto-generated method stub
								return "Delete the node causing this problem.";
							}
						}
				};
			}
		},
		EdgeDeleted(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
				// TODO Auto-generated method stub
				return new IMarkerResolution[]{
						new IMarkerResolution() {
							
							@Override
							public void run(IMarker marker) {
								
								Edge n;
								try {
									String source_ID = (String) marker.getAttribute(IMarker.SOURCE_ID);
									n = (Edge) IDUtil.getModelForID(source_ID);
									if (n == null || !(EcoreUtil.getRootContainer(n) instanceof Module)){
										marker.delete();
										return;
									}
									SimpleDeleteEObjectCommand c = new SimpleDeleteEObjectCommand(n);
									
									c.execute();
									
									//marker.delete();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
							
							@Override
							public String getLabel() {
								// TODO Auto-generated method stub
								return "Delete the edge causing this problem.";
							}
						}
				};
			}
		},
		MissingType(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
				// TODO Auto-generated method stub
				return super.getFixes(marker);
			}
		},	
		InconsistentMapping(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
				return new IMarkerResolution[]{
						new IMarkerResolution() {
							
							@Override
							public void run(IMarker marker) {
			
								try {
									String source_ID = (String) marker.getAttribute(IMarker.SOURCE_ID);
									EObject n = IDUtil.getModelForID(source_ID);
									if (n == null || !(EcoreUtil.getRootContainer(n) instanceof Module)){
										marker.delete();
										return;
									}
									if (n instanceof Mapping){
										((List)n.eContainer().eGet(n.eContainingFeature())).remove(n);
										
									}
									
									marker.delete();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
							
							@Override
							public String getLabel() {
								// TODO Auto-generated method stub
								return "Remove the wrong Mapping.";
							}
						}
				};
			}
		},
		WrongMarker(){
			@Override
			IMarkerResolution[] getFixes(IMarker marker) {
				return new IMarkerResolution[]{
						new IMarkerResolution() {
							
							@Override
							public void run(IMarker marker) {
			
								try {
									String source_ID = (String) marker.getAttribute(IMarker.SOURCE_ID);
									EObject n = IDUtil.getModelForID(source_ID);
									if (n == null || !(EcoreUtil.getRootContainer(n) instanceof Module)){
										marker.delete();
										return;
									}
									if (n instanceof TNode){
										((TNode)n).setMarkerType(null);
									}
									if (n instanceof TEdge){
										
										((TEdge)n).setMarkerType(null);
									}
									if (n instanceof TAttribute){
										
										((TAttribute)n).setMarkerType(null);
									}
									
									marker.delete();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
							
							@Override
							public String getLabel() {
								// TODO Auto-generated method stub
								return "Remove the wrong marker.";
							}
						}
				};
			}
		},	
		;
		
		IMarkerResolution[] getFixes(IMarker marker){
			return new IMarkerResolution[0];
		}
	}
	
	
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		
		ErrorTypes errorType;
		try {
			String s =  (String) marker.getAttribute(TGGMarkerAttributes.errorType);
		if (s == null)
				return new IMarkerResolution[0];
			
			errorType = ErrorTypes.valueOf(s);
			
			
			return errorType.getFixes(marker);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new IMarkerResolution[0];
		
	}




}
