
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:xml="http://www.w3.org/XML/1998/namespace"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:saxon="http://icl.com/saxon"
                saxon:trace="no"
                extension-element-prefixes="saxon"
 		exclude-result-prefixes="#default"
>
<!-- doctype-system="gts.dtd" -->
  <xsl:output doctype-system="gts.dtd"  
              omit-xml-declaration="no"
              method="xml" 
              standalone="no"
              indent="yes"/>
   
   <!-- remove text nodes that contain only whitespaces --> 
    <xsl:strip-space elements = '*' /> 


<xsl:template match="gxl">
	<Document version="1.0">
	   <!-- saxon:if disabled: use only default names
	      <xsl:variable name="origGTSName" select="saxon:if(attr,attr/@name,'Export GTS')"/>
	      <xsl:variable name="origGTSID" select="saxon:if(attr,attr/@id,'IDExportGTS')"/>
	   -->
	   <xsl:variable name="origGTSName" select="'Export GTS'"/>
	   <xsl:variable name="origGTSID" select="'IDExportGTS'"/>
	   <GraphTransformationSystem ID="{$origGTSID}" name="{$origGTSName}">
		<!-- CASE 1: GXL-File has a 'TypeGraph' -->
		<xsl:if test="graph/@id='TypeGraph'">
	   		<!-- saxon:if disabled: use only default names
			   <xsl:variable name="origTypeGraphName" 
				select="saxon:if(graph[@id='TypeGraph']/attr,graph[@id='TypeGraph']/attr/@name,'TypeGraph')"/>
			   <xsl:variable name="origTypeGraphID" 
				select="saxon:if(graph[@id='TypeGraph']/attr,graph[@id='TypeGraph']/attr/@id,'IDTypGraph')"/>
			-->
			<xsl:variable name="origTypeGraphName" select="'TypeGraph'"/>
			<xsl:variable name="origTypeGraphID" select="'IDTypGraph'"/>
			<Types>			
				<xsl:for-each select="graph[@id='TypeGraph']/node">											
					<NodeType ID="NT{generate-id(.)}" abstract="{@abstract}" name="{@id}"> 
						<xsl:apply-templates select="parent"/>
						<xsl:for-each select="attr[@kind='AttrType']">
							<AttrType ID="AT{generate-id(.)}" attrname="{@name}" typename="{.}"/>
						</xsl:for-each>
					</NodeType>
				</xsl:for-each>
				<xsl:for-each select="graph[@id='TypeGraph']/edge">
					<!-- cut ID from name-string -->
					<xsl:variable name="id" select="substring-before(@id,':')"/>
					<xsl:variable name="name" select="substring-after(@id,':')"/>
					<xsl:variable name="test1" select="generate-id()"/>
					<xsl:variable name="test2" 
						select="generate-id(/gxl/graph[@id='TypeGraph']/edge[substring-after(@id,':')=$name])"/>
					<!-- convert EdgeTypes with the same name only once -->
					<xsl:if test="$test1=$test2">
						<EdgeType ID="ET{$id}" name="{$name}">
							<xsl:for-each select="attr[@kind='AttrType']">
								<AttrType ID="AT{generate-id(.)}" attrname="{@name}" typename="{.}"/>
							</xsl:for-each>
						</EdgeType>
					</xsl:if>
				</xsl:for-each>
				<!-- convert TypeGraph for AGG -->
				<Graph ID="{$origTypeGraphID}" name="{$origTypeGraphName}">
					<xsl:for-each select="graph[@id='TypeGraph']/node">
						<Node ID="{generate-id(.)}" type="NT{generate-id(.)}">
							<NodeLayout X="{attr[@name='X']/.}" Y="{attr[@name='Y']/.}"/>
						</Node>
					</xsl:for-each>
					<xsl:for-each select="graph[@id='TypeGraph']/edge">
						<xsl:variable name="from" select="@from"/>
						<xsl:variable name="to" select="@to"/>
						<!-- @id = EdgeID:EdgeName -->
						<xsl:variable name="id" select="substring-before(@id,':')"/>
						<xsl:variable name="name" select="substring-after(@id,':')"/>
						<!-- get ID from the first EdgeType with our TypeName -->
						<xsl:variable name="ETname" 
							select="/gxl/graph[@id='TypeGraph']/edge[substring-after(@id,':')=$name]/@id"/>
						<xsl:variable name="ETid" select="substring-before($ETname,':')"/>
						<Edge ID="{$id}" 
							source="{generate-id(../node[@id=$from])}" 
							sourcemax="{attr[@name='sourcemax']/.}" 
							sourcemin="{attr[@name='sourcemin']/.}" 
							target="{generate-id(../node[@id=$to])}" 
							targetmax="{attr[@name='targetmax']/.}" 
							targetmin="{attr[@name='targetmin']/.}" 
							type="ET{$ETid}">
							<EdgeLayout bendX="{attr[@name='bendX']/.}" 
								bendY="{attr[@name='bendY']/.}" 
								sourceMultiplicityOffsetX="{attr[@name='sourceMultiplicityOffsetX']/.}" 
								sourceMultiplicityOffsetY="{attr[@name='sourceMultiplicityOffsetY']/.}" 
								targetMultiplicityOffsetX="{attr[@name='targetMultiplicityOffsetX']/.}" 
								targetMultiplicityOffsetY="{attr[@name='targetMultiplicityOffsetY']/.}" 
								textOffsetX="{attr[@name='textOffsetX']/.}" 
								textOffsetY="{attr[@name='textOffsetY']/.}"/>
						</Edge>
					</xsl:for-each>
				</Graph>
			</Types>
			<!-- convert InstanceGraph for AGG -->
	   		<!-- saxon:if disabled: use only default names
			   <xsl:variable name="origInstanceGraphName" 
				select="saxon:if(/gxl/graph[@id='InstanceGraph']/attr,/gxl/graph[@id='InstanceGraph']/attr/@name,'InstanceGraph')"/>
			   <xsl:variable name="origInstanceGraphID" 
				select="saxon:if(/gxl/graph[@id='InstanceGraph']/attr,/gxl/graph[@id='InstanceGraph']/attr/@id,'IDInstanceGraph')"/>
			-->
			<xsl:variable name="origInstanceGraphName" select="'InstanceGraph'"/>
			<xsl:variable name="origInstanceGraphID" select="'IDInstanceGraph'"/>
			<Graph ID="{$origInstanceGraphID}" name="{$origInstanceGraphName}">
				<xsl:apply-templates select="/gxl/graph[@id='InstanceGraph']/node"/>
				<xsl:apply-templates select="/gxl/graph[@id='InstanceGraph']/edge"/>
			</Graph>
		</xsl:if>
		<!-- CASE 2: GXL-File has a 'Graph Schema' -->
		<xsl:if test="graph/@id='Schema Graph'">
			<Types>
				<!-- cut out Types from X_Graph help structure -->
				<xsl:for-each select="graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node">
					<NodeType ID="NT{generate-id(.)}" name="{@id}">
						<xsl:for-each select="attr[@kind='AttrType']">
							<AttrType ID="AT{generate-id(.)}" attrname="{@name}" typename="{.}"/>
						</xsl:for-each>
					</NodeType>
				</xsl:for-each>
				<xsl:for-each select="graph[@id='Schema Graph']/edge">
					<EdgeType ID="ET{generate-id(.)}" name="{@id}">
						<xsl:for-each select="attr[@kind='AttrType']">
							<AttrType ID="AT{generate-id(.)}" attrname="{@name}" typename="{.}"/>
						</xsl:for-each>
					</EdgeType>
				</xsl:for-each>
			</Types>
			<!-- convert InstanceGraph for AGG -->
	   		<!-- saxon:if disabled: use only default names
			   <xsl:variable name="origInstanceGraphName" 
				select="saxon:if(/gxl/graph[@id='InstanceGraph']/attr,/gxl/graph[@id='InstanceGraph']/attr/@name,'InstanceGraph')"/>
			   <xsl:variable name="origInstanceGraphID" 
				select="saxon:if(/gxl/graph[@id='InstanceGraph']/attr,/gxl/graph[@id='InstanceGraph']/attr/@id,'IDInstanceGraph')"/>
			-->
			<xsl:variable name="origInstanceGraphName" select="'InstanceGraph'"/>
			<xsl:variable name="origInstanceGraphID" select="'IDInstanceGraph'"/>
			<Graph ID="{$origInstanceGraphID}" name="{$origInstanceGraphName}">
				<xsl:apply-templates select="/gxl/graph[@id='InstanceGraph']/node"/>
				<xsl:apply-templates select="/gxl/graph[@id='InstanceGraph']/edge"/>
			</Graph>
		</xsl:if> 
		<!-- CASE 3: GXL-File has no Type Definitions -->
		<xsl:if test="not ( (graph/@id='TypeGraph') or (graph/@id='Schema Graph') )">
			<Types>
				<!-- NodeTypes -->
				<xsl:for-each select="graph/node/type">
					<xsl:variable name="href" select="../type/@xlink:href"/>
					<xsl:variable name="test1" select="generate-id()"/>
					<xsl:variable name="test2" select="generate-id(/gxl/graph/node/type[@xlink:href=$href])"/>
					<!-- convert NodeTypes with the same name only ones -->
					<xsl:if test="($test1=$test2)">
						<NodeType ID="NT{../@id}" name="{$href}">
							<xsl:for-each select="../attr">
								<xsl:variable name="typename" select="name(*)"/>
								
								<!-- Fujaba 'seq' => Java Vector -->
								<xsl:if test="$typename='seq'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="Vector"/>
								</xsl:if>
								<xsl:if test="$typename='string'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="String"/>
								</xsl:if>
								<xsl:if test="$typename='bool'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="boolean"/>
								</xsl:if>
								<xsl:if test="$typename!='seq' and $typename!='string' and $typename!='bool'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="{name(*)}"/>
								</xsl:if>
							</xsl:for-each>
						</NodeType>
					</xsl:if>
				</xsl:for-each>
				<!-- EdgeTypes -->
				<xsl:for-each select="graph/edge/type">
					<xsl:variable name="href" select="../type/@xlink:href"/>
					<xsl:variable name="test1" select="generate-id()"/>
					<xsl:variable name="test2" select="generate-id(/gxl/graph/edge/type[@xlink:href=$href])"/>
					<!-- convert EdgeTypes with the same name only ones -->
					<xsl:if test="($test1=$test2)">
						<EdgeType ID="ET{../@id}" name="{$href}">
							<xsl:for-each select="../attr">
								<xsl:variable name="typename" select="name(*)"/>
								<!-- Fujaba 'seq' => Java Vector -->
								<xsl:if test="$typename='seq'">
									<AttrType ID="ET{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="Vector"/>
								</xsl:if>
								<xsl:if test="$typename='string'">
									<AttrType ID="ET{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="String"/>
								</xsl:if>
								<xsl:if test="$typename='bool'">
									<AttrType ID="ET{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="boolean"/>
								</xsl:if>
								<xsl:if test="$typename!='seq' and $typename!='string' and $typename!='bool'">
									<AttrType ID="ET{../@id}AT{position()}" 
										attrname="{@name}" 
										typename="{name(*)}"/>
								</xsl:if>
							</xsl:for-each>
						</EdgeType>
					</xsl:if>
				</xsl:for-each>

			</Types>
			
			<!-- Graph => AGG InstanceGraph -->
			<Graph ID="FG1" name="{graph/@id}">

				<xsl:for-each select="graph/node">
						<xsl:variable name="href" select="type/@xlink:href"/>
						<xsl:variable name="NTid" select="/gxl/graph/node/@id[../type/@xlink:href=$href]"/>						
						<Node ID="{@id}" type="NT{$NTid}">						
							<xsl:for-each select="attr">
				
								<xsl:variable name="pos" select="position()"/>
								<!-- Fujaba 'seq' => Java Vector -->
								<xsl:if test="name(*)='seq'">
									<xsl:variable name="string1" select=".//string[position()=1]"/>
									<xsl:variable name="string2" select=".//string[position()=2]"/>
									<Attribute constant="true" type="NT{$NTid}AT{$pos}">
										<Value>
											<java class="java.beans.XMLDecoder" version="1.4.1">
												<object class="java.util.Vector">
													<void method="add">
														<xsl:element name="string">
															<xsl:value-of select="$string1"/>
														</xsl:element>
													</void>
													<void method="add">
														<xsl:element name="string">
															<xsl:value-of select="$string2"/>
														</xsl:element>
													</void>
												</object>
											</java>
										</Value>
									</Attribute>
								</xsl:if>
								<xsl:if test="name(*)!='seq'">
									<Attribute constant="true" type="NT{$NTid}AT{$pos}">
									<xsl:if test="name(*)='bool'"> 
										<Value>
											<boolean>
												<xsl:value-of select="."/>
											</boolean>
										</Value>
									</xsl:if>
									<xsl:if test="name(*)!='bool'"> 
										<Value>
											<xsl:element name="{name(*)}">
												<xsl:value-of select="*"/>
											</xsl:element>
										</Value>
									</xsl:if>
									</Attribute>
								</xsl:if>
							</xsl:for-each>
						</Node>
				</xsl:for-each>

				<xsl:for-each select="graph/edge">
					<xsl:variable name="href" select="type/@xlink:href"/>
					<xsl:variable name="ETid" select="/gxl/graph/edge/@id[../type/@xlink:href=$href]"/>
					<Edge ID="{@id}" source="{@from}" target="{@to}" type="ET{$ETid}">	
							<xsl:for-each select="attr">
								<xsl:variable name="pos" select="position()"/>
								<!-- Fujaba 'seq' => Java Vector -->
								<xsl:if test="name(*)='seq'">
									<xsl:variable name="string1" select=".//string[position()=1]"/>
									<xsl:variable name="string2" select=".//string[position()=2]"/>
									<Attribute constant="true" type="ET{$ETid}AT{$pos}">
										<Value>
											<java class="java.beans.XMLDecoder" version="1.4.1">
												<object class="java.util.Vector">
													<void method="add">
														<xsl:element name="string">
															<xsl:value-of select="$string1"/>
														</xsl:element>
													</void>
													<void method="add">
														<xsl:element name="string">
															<xsl:value-of select="$string2"/>
														</xsl:element>
													</void>
												</object>
											</java>
										</Value>
									</Attribute>
								</xsl:if>
								<xsl:if test="name(*)!='seq'">
									<Attribute constant="true" type="ET{$ETid}AT{$pos}">
									<xsl:if test="name(*)='bool'"> 
										<Value>
											<boolean>
												<xsl:value-of select="."/>
											</boolean>
										</Value>
									</xsl:if>
									<xsl:if test="name(*)!='bool'"> 
										<Value>
											<xsl:element name="{name(*)}">
												<xsl:value-of select="*"/>
											</xsl:element>
										</Value>
									</xsl:if>	
									</Attribute>
								</xsl:if>
							</xsl:for-each>
					</Edge>	
				</xsl:for-each>

			</Graph>
			<!-- END: Case 3 -->
		</xsl:if>
  	   </GraphTransformationSystem>
	</Document>
</xsl:template>

<xsl:template match="node">
	<!-- convert 'node' in 'InstanceGraph' -->
	<xsl:if test="/gxl/graph[@id='TypeGraph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated NodeType-ID -->
		<xsl:variable name="NTid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$href])"/>		
		<Node ID="{@id}" type="NT{$NTid}">
			<xsl:apply-templates select="attr"/>
			<NodeLayout X="{attr[@name='X']}" Y="{attr[@name='Y']}"/>
		</Node>
	</xsl:if>
	<xsl:if test="/gxl/graph[@id='Schema Graph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated NodeType-ID -->
		<xsl:variable name="NTid" select="generate-id(/gxl/graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node[@id=$href])"/>
		<Node ID="{@id}" type="NT{$NTid}">
			<xsl:apply-templates select="attr"/>
			<NodeLayout X="{attr[@name='X']}" Y="{attr[@name='Y']}"/>
		</Node>
	</xsl:if>
</xsl:template>

<xsl:template match="parent">
	<!-- convert 'TypeGraph - parent of node type' -->
		<xsl:variable name="pid" select="@pid"/>
		<xsl:variable name="pID" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$pid])"/>
		<Parent pID="NT{$pID}">
		</Parent>
</xsl:template>

<xsl:template match="attr">
  <!-- convert 'attr' in 'InstanceGraph' -->
  <!-- convert node Attributes -->
  <xsl:if test="name(..)='node'">
				
	<xsl:if test="not (@kind='Layout')">
		<xsl:variable name="name" select="@name"/>
		<xsl:variable name="href" select="../type/@xlink:href"/>
			
					
		<!-- get the generated AttrType-ID -->
	   	<!-- saxon:if disabled: use only default names
		   <xsl:variable name="ATid" select="saxon:if(/gxl/graph/@id='TypeGraph',generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$href]/attr[@name=$name]),generate-id(/gxl/graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node[@id=$href]/attr[@name=$name]))"/>
		-->
				
		<xsl:if test="/gxl/graph/@id='TypeGraph'">
						
			<xsl:variable name="ATid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$href]/attr[@name=$name])"/>
			
			<xsl:if test="$ATid=''">
				<!-- in case of multiple node type inheritance 
				search throw parents of 4 generations only 			
				-->
				<xsl:variable name="PNT" select="/gxl/graph[@id='TypeGraph']/node[@id=$href]/parent/@pid"/>
				<xsl:variable name="PATid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/attr[@name=$name])"/>	
				<xsl:if test="$PATid=''">
					<xsl:variable name="PNT" select="/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/parent/@pid"/>
					<xsl:variable name="PATid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/attr[@name=$name])"/>	
					<xsl:if test="$PATid=''">
						<xsl:variable name="PNT" select="/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/parent/@pid"/>
						<xsl:variable name="PATid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/attr[@name=$name])"/>	
						<xsl:if test="$PATid=''">
							<xsl:variable name="PNT" select="/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/parent/@pid"/>
							<xsl:variable name="PATid" select="generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$PNT]/attr[@name=$name])"/>	
				
							<Attribute constant="true" type="AT{$PATid}">
								<xsl:if test="freeType/java"> 
									<Value>
										<xsl:copy-of select="freeType/java"/>
									</Value>
								</xsl:if>
								<xsl:if test="freeType/object"> 
									<Value>
										<xsl:copy-of select="freeType/object"/>
									</Value>
								</xsl:if>
								<xsl:if test="bool"> 
									<Value>
										<boolean>
											<xsl:value-of select="."/>
										</boolean>
									</Value>
								</xsl:if>
								<xsl:if test="int | string | float | char | double | short | long"> 
									<Value>
										<xsl:copy-of select="int | string | float | char | double | short | long"/>
									</Value>
								</xsl:if>
							</Attribute>
						</xsl:if>
						<Attribute constant="true" type="AT{$PATid}">
							<xsl:if test="freeType/java"> 
								<Value>
									<xsl:copy-of select="freeType/java"/>
								</Value>
							</xsl:if>
							<xsl:if test="freeType/object"> 
								<Value>
									<xsl:copy-of select="freeType/object"/>
								</Value>
							</xsl:if>
							<xsl:if test="bool"> 
								<Value>
									<boolean>
										<xsl:value-of select="."/>
									</boolean>
								</Value>
							</xsl:if>
							<xsl:if test="int | string | float | char | double | short | long"> 
								<Value>
									<xsl:copy-of select="int | string | float | char | double | short | long"/>
								</Value>
							</xsl:if>
						</Attribute>
					</xsl:if>
					<Attribute constant="true" type="AT{$PATid}">
						<xsl:if test="freeType/java"> 
							<Value>
								<xsl:copy-of select="freeType/java"/>
							</Value>
						</xsl:if>
						<xsl:if test="freeType/object"> 
							<Value>
								<xsl:copy-of select="freeType/object"/>
							</Value>
						</xsl:if>
						<xsl:if test="bool"> 
							<Value>
								<boolean>
									<xsl:value-of select="."/>
								</boolean>
							</Value>
						</xsl:if>
						<xsl:if test="int | string | float | char | double | short | long"> 
							<Value>
								<xsl:copy-of select="int | string | float | char | double | short | long"/>
							</Value>
						</xsl:if>
					</Attribute>
				</xsl:if>
				<Attribute constant="true" type="AT{$PATid}">
					<xsl:if test="freeType/java"> 
						<Value>
							<xsl:copy-of select="freeType/java"/>
						</Value>
					</xsl:if>
					<xsl:if test="freeType/object"> 
						<Value>
							<xsl:copy-of select="freeType/object"/>
						</Value>
					</xsl:if>
					<xsl:if test="bool"> 
						<Value>
							<boolean>
								<xsl:value-of select="."/>
							</boolean>
						</Value>
					</xsl:if>
					<xsl:if test="int | string | float | char | double | short | long"> 
						<Value>
							<xsl:copy-of select="int | string | float | char | double | short | long"/>
						</Value>
					</xsl:if>
				</Attribute>
			</xsl:if>	
											
			<Attribute constant="true" type="AT{$ATid}">
				<xsl:if test="freeType/java"> 
					<Value>
						<xsl:copy-of select="freeType/java"/>
					</Value>
				</xsl:if>
				<xsl:if test="freeType/object"> 
					<Value>
						<xsl:copy-of select="freeType/object"/>
					</Value>
				</xsl:if>
				<xsl:if test="bool"> 
					<Value>
						<boolean>
							<xsl:value-of select="."/>
						</boolean>
					</Value>
				</xsl:if>
				<xsl:if test="int | string | float | char | double | short | long"> 
					<Value>
						<xsl:copy-of select="int | string | float | char | double | short | long"/>
					</Value>
				</xsl:if>
			</Attribute>
		</xsl:if>
			
		<xsl:if test="/gxl/graph/@id='Schema Graph'">
			<xsl:variable name="ATid" select="generate-id(/gxl/graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node[@id=$href]/attr[@name=$name])"/>
			<Attribute constant="true" type="AT{$ATid}">
				<xsl:if test="freeType/java"> 
					<Value>
						<xsl:copy-of select="freeType/java"/>
					</Value>
				</xsl:if>
				<xsl:if test="freeType/object"> 
					<Value>
						<xsl:copy-of select="freeType/object"/>
					</Value>
				</xsl:if>
				<xsl:if test="bool"> 
					<Value>
						<boolean>
							<xsl:value-of select="."/>
						</boolean>
					</Value>
				</xsl:if>
				<xsl:if test="int | string | float | char | double | short | long"> 
					<Value>
						<xsl:copy-of select="int | string | float | char | double | short | long"/>
					</Value>
				</xsl:if>
			</Attribute>
		</xsl:if>
	</xsl:if>
  </xsl:if> <!-- node -->
  <!-- convert edge Attributes -->
  <xsl:if test="name(..)='edge'">
	<xsl:if test="not (@kind='Layout')">
		<xsl:variable name="name" select="@name"/>
		<xsl:variable name="href" select="../type/@xlink:href"/>
		<!-- get the generated AttrType-ID -->
	   	<!-- saxon:if disabled: use only default names
		   <xsl:variable name="ATid" select="saxon:if(/gxl/graph/@id='TypeGraph',generate-id(/gxl/graph[@id='TypeGraph']/node[@id=$href]/attr[@name=$name]),generate-id(/gxl/graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node[@id=$href]/attr[@name=$name]))"/>
		-->
		<xsl:if test="/gxl/graph/@id='TypeGraph'">
			<xsl:variable name="ATid" select="generate-id(/gxl/graph[@id='TypeGraph']/edge[@id=$href]/attr[@name=$name])"/>
			<Attribute constant="true" type="AT{$ATid}">
				<xsl:if test="freeType/java"> 
					<Value>
						<xsl:copy-of select="freeType/java"/>
					</Value>
				</xsl:if>
				<xsl:if test="freeType/object"> 
					<Value>
						<xsl:copy-of select="freeType/object"/>
					</Value>
				</xsl:if>
				<xsl:if test="bool"> 
					<Value>
						<boolean>
							<xsl:value-of select="."/>
						</boolean>
					</Value>
				</xsl:if>
				<xsl:if test="int | string | float | char | double | short | long"> 
					<Value>
						<xsl:copy-of select="int | string | float | char | double | short | long"/>
					</Value>
				</xsl:if>
			</Attribute>
		</xsl:if>
		<xsl:if test="/gxl/graph/@id='Schema Graph'">
			<xsl:variable name="ATid" select="generate-id(/gxl/graph[@id='Schema Graph']/edge[@id=$href]/attr[@name=$name])"/>
			<Attribute constant="true" type="AT{$ATid}">
				<xsl:if test="freeType/java"> 
					<Value>
						<xsl:copy-of select="freeType/java"/>
					</Value>
				</xsl:if>
				<xsl:if test="freeType/object"> 
					<Value>
						<xsl:copy-of select="freeType/object"/>
					</Value>
				</xsl:if>
				<xsl:if test="bool"> 
					<Value>
						<boolean>
							<xsl:value-of select="."/>
						</boolean>
					</Value>
				</xsl:if>
				<xsl:if test="int | string | float | char | double | short | long"> 
					<Value>
						<xsl:copy-of select="int | string | float | char | double | short | long"/>
					</Value>
				</xsl:if>
			</Attribute>
		</xsl:if>
	</xsl:if>
    </xsl:if> <!-- edge -->
</xsl:template>

<xsl:template match="edge">
	<!-- convert 'edge' in 'InstanceGraph' -->
	<xsl:if test="/gxl/graph[@id='TypeGraph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated EdgeType-ID -->
		<xsl:variable name="id" select="substring-before($href,':')"/>
		<xsl:variable name="name" select="substring-after($href,':')"/>
		<!-- get ID from the first EdgeType with our TypeName -->
		<xsl:variable name="ETname" 
			select="/gxl/graph[@id='TypeGraph']/edge[substring-after(@id,':')=$name]/@id"/>
		<xsl:variable name="ETid" select="substring-before($ETname,':')"/>
		<Edge ID="{@id}" source="{@from}" target="{@to}" type="ET{$ETid}">
			<xsl:apply-templates select="attr"/>
			<EdgeLayout bendX="{attr[@name='bendX']}" bendY="{attr[@name='bendY']}"
				textOffsetX="{attr[@name='textOffsetX']}" textOffsetY="{attr[@name='textOffsetY']}"/>
		</Edge>
	</xsl:if>
	<xsl:if test="/gxl/graph[@id='Schema Graph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated EdgeType-ID -->
		<xsl:variable name="ETid" select="generate-id(/gxl/graph[@id='Schema Graph']/edge[@id=$href])"/>
		<Edge ID="{@id}" source="{@from}" target="{@to}" type="ET{$ETid}">
			<xsl:apply-templates select="attr"/>
			<EdgeLayout bendX="{attr[@name='bendX']}" bendY="{attr[@name='bendY']}"
				textOffsetX="{attr[@name='textOffsetX']}" textOffsetY="{attr[@name='textOffsetY']}"/>
		</Edge>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>

