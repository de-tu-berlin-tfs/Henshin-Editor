/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author nam
 * 
 */
public final class ResourceUtil {
	public static enum ICONS {
		
		ATTRIBUTE("attr"), //$NON-NLS-1$
		
		CHECK("check"), //$NON-NLS-1$
		
		CLEAR("clear"), //$NON-NLS-1$
		
		COMPOUND_ACTIVITY("compound_act"), //$NON-NLS-1$
		
		DEFAULT_FOLDER("default_folder"), //$NON-NLS-1$
		
		DELTE_INPUT_PARAMETER("delete_inputparameter"), //$NON-NLS-1$
		
		DELTE_OUTPUT_PARAMETER("delete_outputparameter"), //$NON-NLS-1$
		
		DUMMY("dummy"), //$NON-NLS-1$
		
		EDGE("edge"), //$NON-NLS-1$
		
		EPACKAGE("epackage"), //$NON-NLS-1$
		
		EPACKAGE_FILTER("epackage_filter"), //$NON-NLS-1$
		
		EPACKAGE_FOLDER("epackage_folder"), //$NON-NLS-1$
		
		EXIST("exist"), //$NON-NLS-1$
		
		EXPORT("export"), //$NON-NLS-1$
		
		FLOW_CTRL_COUNTED("flow_ctrl_counted"), //$NON-NLS-1$
		
		FLOW_DIAGRAM("flow_diagram"), //$NON-NLS-1$
		
		FLOW_DIAGRAM_FOLDER("flow_diagram_folder"), //$NON-NLS-1$
		
		GRAPH("graph"), //$NON-NLS-1$
		
		GRAPH_FOLDER("graph_folder"), //$NON-NLS-1$
		
		INPUT_PARAMETER("inputparameter"), //$NON-NLS-1$
		
		LINK("link"), //$NON-NLS-1$
		
		LOOP("loop"), //$NON-NLS-1$
		
		MATCH_SEARCH_TOOL("type_search"), //$NON-NLS-1$
		
		MODEL_SEARCH_TOOL("model_search"), //$NON-NLS-1$
		
		NODE("node"), //$NON-NLS-1$
		
		NOT_EXIST("not_exist"), //$NON-NLS-1$
		
		OUTPUT_PARAMETER("outputparameter"), //$NON-NLS-1$
		
		PARAM_MAPPING("param_mapping"), PARAMETER("parameter"), //$NON-NLS-1$
		
		PARAMETER_FOLDER("parameter_folder"),//$NON-NLS-1$
		
		PLAY("play"),//$NON-NLS-1$
		
		RULE("rule"), //$NON-NLS-1$
		
		RULE_ACT_TOOL("rule_act_tool"), //$NON-NLS-1$
		
		RULE_COND_ACT_TOOL("rule_cond_act_tool"), //$NON-NLS-1$
		
		RULE_FOLDER("rule_folder"), //$NON-NLS-1$
		
		RULE_LINK("rule_tool"), //$NON-NLS-1$
		
		RULER("ruler"), //$NON-NLS-1$
		
		SIMPLE_ACT("act_tool"), //$NON-NLS-1$
		
		SORT("sort"), //$NON-NLS-1$
		
		TRANS_SYS("transsys"), //$NON-NLS-1$
		
		TRANS_UNIT("tU"), //$NON-NLS-1$ 
		
		TRANS_UNIT_FOLDER("t.closed"), //$NON-NLS-1$ 
		
		TYPE_SEARCH_TOOL("type_search"); //$NON-NLS-1$

	/**
	 * 
	 */
		private String fileName;

		/**
		 * @param fileName
		 */
		private ICONS(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * @return
		 */
		public ImageDescriptor descr(int size) {
			try {
				return IconUtil.getDescriptor(fileName + size + ".png");//$NON-NLS-1$
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * @return
		 */
		public Image img(int size) {
			try {
				return IconUtil.getIcon(fileName + size + ".png");//$NON-NLS-1$
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 
	 */
	private ResourceUtil() {
	}
}
