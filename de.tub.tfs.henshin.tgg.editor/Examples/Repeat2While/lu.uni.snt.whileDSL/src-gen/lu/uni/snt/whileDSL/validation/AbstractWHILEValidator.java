/*
 * generated by Xtext
 */
package lu.uni.snt.whileDSL.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public class AbstractWHILEValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(lu.uni.snt.whileDSL.wHILE.WHILEPackage.eINSTANCE);
		return result;
	}
}