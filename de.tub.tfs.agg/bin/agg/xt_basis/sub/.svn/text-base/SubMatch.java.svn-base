package agg.xt_basis.sub;

import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;

/**
 * @version $Id: SubMatch.java,v 1.2 2010/09/23 08:28:20 olga Exp $
 * @author $Author: olga $
 * 
 * @deprecated not more supported
 */
public class SubMatch extends Match {
	private Match itsSuperMatch;

	private OrdinarySubMorphism itsSubMatchMorph;

	protected SubMatch(Match supermatch, SubRule subrule, SubGraph subimage) {
		super(subrule, subimage);
		this.itsSuperMatch = supermatch;
//		itsSubMatchMorph = supermatch.createSubMorphism((SubGraph) subrule
//				.getOriginal(), subimage);
	}

	public void dispose() {
		this.itsSubMatchMorph.dispose();
		super.dispose();
	}

	public final Match getSuperMatch() {
		return this.itsSuperMatch;
	}

	public final Graph getOriginal() {
		return this.itsSubMatchMorph.getOriginal();
	}

	public final Graph getImage() {
		return this.itsSubMatchMorph.getImage();
	}

	public final void addMapping(GraphObject o, GraphObject i)
			throws BadMappingException {
		this.itsSubMatchMorph.addMapping(o, i);
	}

	public final void removeMapping(GraphObject o) {
		this.itsSubMatchMorph.removeMapping(o);
	}

}

