

import java.io.File;

import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTra;
import agg.xt_basis.Graph;


public class IterationCD2RDBM {
	static int NN;
	static String fileName = "";
	static String times = "";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			CD2RDBM_FT.helpText();
		} else {
			handleInput(args);
			//CD2RDBM_FT sequence;
			String time="";
			GraGra gragra;
			GraTra gratra;

			gragra = CD2RDBM_FT.load(fileName);
//			gragra.cloneGraph();
			
			gratra = new DefaultGraTraImpl();
			gratra.setGraGra(gragra);
			gratra.setHostGraph(gragra.getGraph());
			String graname = gragra.getGraph().getName();
			
			Graph hostGraphCopy;

			long trafoStartTime;
			long trafoEndTime;
			for(int i=0; i<NN; i++){

				hostGraphCopy = gragra.getGraph().copy();
				hostGraphCopy.setName(graname.concat("_".concat(String.valueOf(i))));
				gragra.addGraph(hostGraphCopy);
	
				//gratra.unsetStop();
				//setHostGraph(g2);
				System.out.println("\n Run "+i+", Start-Size:"+gragra.getGraph().getSize()+", " +
						""+gragra.getListOfGraphs().size()+" graphs");
								
				trafoStartTime=System.currentTimeMillis();
				gratra.transform();
				trafoEndTime=System.currentTimeMillis();				
				
				time=(trafoEndTime-trafoStartTime)+"";
				System.out.println("Used time for graph transformation: "+time+"(extern)");
				times= times+time+";";
					
				if (i < NN-1) {
				gragra.destroyAllMatches();
				gragra.destroyGraph(gragra.getGraph());
//				gragra.removeGraph(gragra.getGraph());
				gragra.resetGraph(hostGraphCopy);
				gratra.setHostGraph(gragra.getGraph());
				}
			}		
			System.out.println("\nTimes: "+times);

			gragra.save(System.getProperties().getProperty("user.dir")+File.separator+"OUT-"+gragra.getFileName());
		}

	}

	
	static void handleInput(String[] args){
		String fn = "";
		NN = -1;
		for (int i=0; i<args.length; i++) {
			if(NN == -1) {
				String nn = args[i];
				try {
					NN = (Integer.valueOf(nn)).intValue();
					continue;
				} catch (NumberFormatException ex) {}
			}
			

			
			if(fn.equals("")) {
				if (args[i].endsWith(".ggx")) 
					fileName = args[i];
				else 
					fileName = args[i] + ".ggx";
			}
		}
		
		if(NN == -1)
			NN = 1;
	}
}
