<?xml version="1.0" encoding="UTF-8"?>

<!-- java org.apache.xalan.xslt.Process -in -xsl -out -->


<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 

<xsl:output method="xml" version="1.0" encoding="UTF-8" doctype-system="gxl.dtd" indent="yes" /> 


<xsl:template match="/"> 

<gxl xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xalan="http://xml.apache.org/xalan" xmlns:lxslt="http://xml.apache.org/xslt">

<attr id="I1" name="{concat('UMLClass_to_GXL_',/ecore:EPackage/@name)}"/>   <!--  -->

<graph id="Schema Graph" edgeids="true">
<edge id="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:" from="X" to="X"/>
<edge id="s%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:" from="X" to="X"/>
<edge id="t%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:" from="X" to="X"/>
<node id="X">

<graph id="X_Graph" edgeids="true">

<node id="%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
<node id="Class%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:">
<attr name="name" kind="AttrType">
<string>String</string>
</attr>
</node>

<node id="Association%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>


<node id="SuperType%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>


<node id="AssEnd%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:">
<attr name="name" kind="AttrType">
<string>String</string>
</attr>
<attr name="min" kind="AttrType">
<string>String</string>
</attr>
<attr name="max" kind="AttrType">
<string>String</string>
</attr>
<attr name="navigable" kind="AttrType">
<string>boolean</string>
</attr>
<attr name="composition" kind="AttrType">
<string>boolean</string>
</attr>
</node>

<node id="Attribute%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:">
<attr name="name" kind="AttrType">
<string>String</string>
</attr>
<attr name="type" kind="AttrType">
<string>String</string>
</attr>
</node>

</graph>
</node>
</graph>


<graph id="InstanceGraph">
 <attr id="I2" name="Graph"/>
    <xsl:for-each select="ecore:EPackage/eClassifiers" >  
   
	<node id="N{generate-id(.)}">
	<type xlink:href="Class%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
	<attr name="name">
	<string> <xsl:value-of select="@name"/> </string>
	</attr>
	<attr name="X" kind="Layout">
	<int>375</int>
	</attr>
	<attr name="Y" kind="Layout">
	<int>144</int>
	</attr>
	</node>
   
    </xsl:for-each> 





<!-- create nodes for AssEnd based on the navigability -->

	<xsl:for-each select="ecore:EPackage/eClassifiers">
		<xsl:for-each select="eStructuralFeatures[@xsi:type='ecore:EReference']">


			<xsl:choose>
			<xsl:when test="@eOpposite !=''">
     
				<node id="N{generate-id(.)}">
				<type xlink:href="AssEnd%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="name">
				<string> <xsl:value-of select="@name"/> </string>
				</attr>	
				<attr name="min">
				<string> 
				
					<xsl:choose>
					   <xsl:when test="@lowerBound='1' and @upperBound='-1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@lowerBound='1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@upperBound='-1'">
					      <xsl:text > 0  </xsl:text>
					   </xsl:when>
					   <xsl:otherwise>
					      <xsl:text > 0  </xsl:text>
					   </xsl:otherwise>
					</xsl:choose>				

				</string>
				</attr>
				<attr name="max">
				<string>
					<xsl:choose>
					   <xsl:when test="@lowerBound='1' and @upperBound='-1'">
					      <xsl:text > *  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@lowerBound='1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@upperBound='-1'">
					      <xsl:text > *  </xsl:text>
					   </xsl:when>
					   <xsl:otherwise>
					      <xsl:text > 1  </xsl:text>
					   </xsl:otherwise>
					</xsl:choose>

				</string>
				</attr>
				<attr name="navigable">
				<bool>true</bool>

				</attr>
				<attr name="composition">


					<xsl:choose>
					   <xsl:when test="@containment='true'">
					      <bool>false</bool>
					   </xsl:when>
					   <xsl:otherwise>
					     
						<xsl:for-each select="//eStructuralFeatures[@eOpposite=concat('#//',current()/../@name,'/',current()/@name)]">

						   <xsl:choose>
						     <xsl:when test="@containment='true'"> 
							<bool>true</bool>
						     </xsl:when>
						     <xsl:otherwise>
							<bool>false</bool>
					             </xsl:otherwise>
                    				   </xsl:choose>

						</xsl:for-each>



					   </xsl:otherwise>
					</xsl:choose>				


				</attr>
				<attr name="X" kind="Layout">
				<int>330</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>228</int>
				</attr>
				</node>
			</xsl:when>
			<xsl:otherwise>

				<node id="N{generate-id(.)}">
				<type xlink:href="AssEnd%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="name">
				<string> <xsl:value-of select="@name"/> </string>
				</attr>	
				<attr name="min">
				<string> 
				
					<xsl:choose>
					   <xsl:when test="@lowerBound='1' and @upperBound='-1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@lowerBound='1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@upperBound='-1'">
					      <xsl:text > 0  </xsl:text>
					   </xsl:when>
					   <xsl:otherwise>
					      <xsl:text > 0  </xsl:text>
					   </xsl:otherwise>
					</xsl:choose>			

				</string>
				</attr>
				<attr name="max">
				<string>
					<xsl:choose>
					   <xsl:when test="@lowerBound='1' and @upperBound='-1'">
					      <xsl:text > *  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@lowerBound='1'">
					      <xsl:text > 1  </xsl:text>
					   </xsl:when>
					   <xsl:when test="@upperBound='-1'">
					      <xsl:text > *  </xsl:text>
					   </xsl:when>
					   <xsl:otherwise>
					      <xsl:text > 1  </xsl:text>
					   </xsl:otherwise>
					</xsl:choose>

				</string>
				</attr>
				<attr name="navigable">
				<bool>true</bool>
				</attr>
				<attr name="composition">
 		                <bool>false</bool>
				</attr>
				<attr name="X" kind="Layout">
				<int>330</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>228</int>
				</attr>
				</node>


				<node id="NNV{generate-id(.)}">  <!-- NNV node not navigable -->
				<type xlink:href="AssEnd%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="name">
				<string/>
				</attr>	
				<attr name="min">
				<string/> 
				</attr>
				<attr name="max">
				<string/>
				</attr>
				<attr name="navigable">
				<bool>false</bool>
				</attr>
				<attr name="composition">
					<xsl:choose>
					   <xsl:when test="@containment='true'">
					      <bool>true</bool>
					   </xsl:when>
					   <xsl:otherwise>
					      <bool>false</bool>
					   </xsl:otherwise>
					</xsl:choose>
				</attr>
				<attr name="X" kind="Layout">
				<int>330</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>228</int>
				</attr>
				</node>


			</xsl:otherwise>
			</xsl:choose>
    
		</xsl:for-each>
	</xsl:for-each>



	   <xsl:for-each select="//eStructuralFeatures[@xsi:type='ecore:EReference']"> 

		<xsl:choose>
		<xsl:when test="@eOpposite!=''">

			<edge id="E{generate-id(.)}" from="N{generate-id(//eClassifiers[@name=substring-after(current()/@eType,'#//')])}" to="N{generate-id(.)}" >
			<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
			<attr name="bendX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="bendY" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetY" kind="Layout">
			<int>-22</int>
			</attr>
			</edge>

		</xsl:when>
		<xsl:otherwise>
	
			<edge id="E{generate-id(.)}" from="N{generate-id(//eClassifiers[@name=substring-after(current()/@eType,'#//')])}" to="N{generate-id(.)}" >
			<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
			<attr name="bendX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="bendY" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetY" kind="Layout">
			<int>-22</int>
			</attr>
			</edge>


			<edge id="ENV{generate-id(.)}" from="N{generate-id(..)}" to="NNV{generate-id(.)}" >
			<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
			<attr name="bendX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="bendY" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetX" kind="Layout">
			<int>0</int>
			</attr>
			<attr name="textOffsetY" kind="Layout">
			<int>-22</int>
			</attr>
			</edge>



		</xsl:otherwise>
		</xsl:choose>

	   </xsl:for-each>  
	        


<!-- create edges between AssEnd and Association -->


	<xsl:for-each select="//eStructuralFeatures[@xsi:type='ecore:EReference']"> 

	<xsl:choose>
	<xsl:when test="@eOpposite!=''">

		<xsl:choose>			
			<xsl:when test="number(translate(generate-id(.),'1234567890qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM_-#/','123456789012345678901234567890123456789012345678901234567890123456')) &lt; number(translate(generate-id(//eStructuralFeatures[@xsi:type='ecore:EReference' and @name=substring-after(substring-after(current()/@eOpposite,'#//'),'/') and @eOpposite=concat('#//',current()/../@name,'/',current()/@name)]),'1234567890qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM_-#/','123456789012345678901234567890123456789012345678901234567890123456'))">

				<edge id="EASS{generate-id(.)}" from="{concat('NASS',generate-id(.),generate-id(//eStructuralFeatures[@xsi:type='ecore:EReference' and @name=substring-after(substring-after(current()/@eOpposite,'#//'),'/') and @eOpposite=concat('#//',current()/../@name,'/',current()/@name)]))}" to="N{generate-id(.)}">	
				
				<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>	
			</xsl:when>
			
			<xsl:otherwise>
			     
				<edge id="EASS{generate-id(.)}" from="{concat('NASS',generate-id(//eStructuralFeatures[@xsi:type='ecore:EReference' and @name=substring-after(substring-after(current()/@eOpposite,'#//'),'/') and @eOpposite=concat('#//',current()/../@name,'/',current()/@name)]),generate-id(.))}" to="N{generate-id(.)}">			
				<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>	
	
			</xsl:otherwise>
		</xsl:choose>

	</xsl:when>
	<xsl:otherwise>

				<edge id="EASS{generate-id(.)}" from="{concat('NASS','N',generate-id(.),'NNV',generate-id(.))}" to="N{generate-id(.)}">						<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>

				<edge id="EASSO{generate-id(.)}" from="{concat('NASS','N',generate-id(.),'NNV',generate-id(.))}" to="NNV{generate-id(.)}">					<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>


	</xsl:otherwise>
	</xsl:choose>

	</xsl:for-each>



<!-- create the Associtation -->


	<xsl:for-each select="//eStructuralFeatures[@xsi:type='ecore:EReference']"> 


	<xsl:choose>
	<xsl:when test="@eOpposite!=''">
		
			<xsl:if test="number(translate(generate-id(.),'1234567890qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM_-#/','123456789012345678901234567890123456789012345678901234567890123456')) &lt; number(translate(generate-id(//eStructuralFeatures[@xsi:type='ecore:EReference' and @name=substring-after(substring-after(current()/@eOpposite,'#//'),'/') and @eOpposite=concat('#//',current()/../@name,'/',current()/@name)]),'1234567890qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM_-#/','123456789012345678901234567890123456789012345678901234567890123456'))">

				<node id="{concat('NASS',generate-id(.),generate-id(//eStructuralFeatures[@xsi:type='ecore:EReference' and @name=substring-after(substring-after(current()/@eOpposite,'#//'),'/') and @eOpposite=concat('#//',current()/../@name,'/',current()/@name)]))}">	

				<type xlink:href="Association%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="X" kind="Layout">
				<int>221</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>156</int>
				</attr>
				</node>

			</xsl:if>
	</xsl:when>
	<xsl:otherwise>			

				<node id="{concat('NASS','N',generate-id(.),'NNV',generate-id(.))}">	

				<type xlink:href="Association%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="X" kind="Layout">
				<int>221</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>156</int>
				</attr>
				</node>



	</xsl:otherwise>
	</xsl:choose>

	</xsl:for-each>





<!-- create attributes -->

	<xsl:for-each select="//eStructuralFeatures[@xsi:type='ecore:EAttribute']"> 

		<node id="NAttrib{generate-id(.)}">
		<type xlink:href="Attribute%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
		<attr name="name">
		<string> <xsl:value-of select="@name"/> </string>
		</attr>
		<attr name="type">
		<string> <xsl:value-of select="substring-after(@eType,'#//')"/> </string>
		</attr>
		<attr name="X" kind="Layout">
		<int>200</int>
		</attr>
		<attr name="Y" kind="Layout">
		<int>50</int>
		</attr>
		</node>

	</xsl:for-each>



<!-- create links between Class and Attributes -->

	<xsl:for-each select="//eStructuralFeatures[@xsi:type='ecore:EAttribute']"> 

				<edge id="EAttrib{generate-id(.)}" from="N{generate-id(..)}" to="NAttrib{generate-id(.)}" >			
				<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>	

	</xsl:for-each>



<!-- create inheritance links among classes -->


	<xsl:for-each select="ecore:EPackage/eClassifiers[@eSuperTypes]"> 


				<node id="NSuperType{generate-id(.)}">	
				<type xlink:href="SuperType%:RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"/>
				<attr name="X" kind="Layout">
				<int>500</int>
				</attr>
				<attr name="Y" kind="Layout">
				<int>300</int>
				</attr>
				</node>

				<edge id="ESuperType1{generate-id(.)}" from="N{generate-id(.)}" to="NSuperType{generate-id(.)}">					<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>	


				<edge id="ESuperType2{generate-id(.)}" from="NSuperType{generate-id(.)}" to="N{generate-id(//eClassifiers[@name=substring-after(current()/@eSuperTypes,'#//')])}" >							

				<type xlink:href="%:SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"/>
				<attr name="bendX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="bendY" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetX" kind="Layout">
				<int>0</int>
				</attr>
				<attr name="textOffsetY" kind="Layout">
				<int>-22</int>
				</attr>
				</edge>	



	

	</xsl:for-each>






</graph>



</gxl>
</xsl:template>
</xsl:stylesheet>



