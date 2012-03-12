/**
 * 
 */
package agg.gui.animation;


/**
 * @author olga
 *
 */
public class AnimationParam {

	public int kind = NodeAnimation.JUMP;
	
	public int step = 5;
	
	public int delay = 10;
	
	public int plus = 0;
	
	public String targetEdgeTypeName;
	
	public AnimationParam(
			final int animationKind, 
			final int animationStep, 
			final int animationDelay,
			final int endPositionPlus) {
		this.kind = animationKind;
		this.step = animationStep;
		this.delay = animationDelay;
		this.plus = endPositionPlus;
	}
	
	public AnimationParam(
			final int animationKind, 
			final int animationStep, 
			final int animationDelay,
			final int endPositionPlus,
			final String endEdgeTypeName) {
		
		this(animationKind, animationStep, animationDelay, endPositionPlus);
		setTargetEdgeTypeName(endEdgeTypeName);
	}
	
	public String getKind() {
		String out = "JUMP";
		if (this.kind == NodeAnimation.WORM)
			out = "WORM";
		else if (this.kind == NodeAnimation.CROSS)
			out = "CROSS";
		else if (this.kind == NodeAnimation.COMBI_CROSS)
			out = "COMBI_CROSS";
		return out;
	}
	
	public String getStep() {
		return String.valueOf(this.step);
	}
	
	public String getDelay() {
		return String.valueOf(this.delay);
	}

	public String getEndPlus() {
		return String.valueOf(this.plus);
	}
	
//	public Type getTargetEdgeType() {
//		return this.targetEdgeType;
//	}
	
	public String getTargetEdgeTypeName() {
		return this.targetEdgeTypeName;
	}
	
	public void setKind(final String kindstr) {
		this.kind = NodeAnimation.JUMP;
		if (kindstr.equals("WORM"))
			this.kind = NodeAnimation.WORM;
		else if (kindstr.equals("CROSS"))
			this.kind = NodeAnimation.CROSS;
		else if (kindstr.equals("COMBI_CROSS"))
			this.kind = NodeAnimation.COMBI_CROSS;
	}
	
	public void setStep(final String stepstr) {
		try{
			this.step = Integer.valueOf(stepstr).intValue();
		} catch(Exception ex) {
			this.step = 0;
		}
	}
	
	public void setDelay(final String delaystr) {
		try{
			this.delay = Integer.valueOf(delaystr).intValue();
		} catch(Exception ex) {
			this.delay = 0;
		}
	}
	
	public void setEndPlus(final String plusstr) {
		try{
			this.plus = Integer.valueOf(plusstr).intValue();
		} catch(Exception ex) {
			this.plus = 0;
		}
	}
		
	public void setTargetEdgeTypeName(final String edgeTypeName) {
		this.targetEdgeTypeName = edgeTypeName;
	}
}
