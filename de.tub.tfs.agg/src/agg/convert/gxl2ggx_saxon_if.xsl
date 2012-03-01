
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

  <xsl:output doctype-system="gts.dtd"  
              omit-xml-declaration="no"
              method="xml" 
              standalone="no"
              indent="yes"/>
   
   <!-- remove text nodes that contain only whitespaces --> 
    <xsl:strip-space elements = '*' /> 


<xsl:template match="gxl">
	<Document version="1.0">
	   <xsl:variable name="origGTSName" select="saxon:if(attr,attr/@name,'Export GTS')"/>
	   <xsl:variable name="origGTSID" select="saxon:if(attr,attr/@id,'IDExportGTS')"/>
	   <GraphTransformationSystem ID="{$origGTSID}" name="{$origGTSName}">
		<!-- CASE 1: GXL-File has a 'Type Graph' -->
		<xsl:if test="graph/@id='Type Graph'">
			<xsl:variable name="origTypeGraphName" 
				select="saxon:if(graph[@id='Type Graph']/attr,graph[@id='Type Graph']/attr/@name,'Type Graph')"/>
			<xsl:variable name="origTypeGraphID" 
				select="saxon:if(graph[@id='Type Graph']/attr,graph[@id='Type Graph']/attr/@id,'IDTypGraph')"/>
			<Types>
				<xsl:for-each select="graph[@id='Type Graph']/node">
					<NodeType ID="NT{generate-id(.)}" name="{@id}">
						<xsl:for-each select="attr[@kind='AttrType']">
							<AttrType ID="AT{generate-id(.)}" attrname="{@name}" typename="{.}"/>
						</xsl:for-each>
					</NodeType>
				</xsl:for-each>
				<xsl:for-each select="graph[@id='Type Graph']/edge">

					<!-- cut ID from name-string -->
					<xsl:variable name="id" select="substring-before(@id,':')"/>
					<xsl:variable name="name" select="substring-after(@id,':')"/>
					<xsl:variable name="test1" select="generate-id()"/>
					<xsl:variable name="test2" 
						select="generate-id(/gxl/graph[@id='Type Graph']/edge[substring-after(@id,':')=$name])"/>
					<!-- convert EdgeTypes with the same name only once -->
					<xsl:if test="$test1=$test2">
						<EdgeType ID="ET{$id}" name="{$name}"/>
					</xsl:if>
				</xsl:for-each>
				<!-- convert Type Graph for AGG -->
				<Graph ID="{$origTypeGraphID}" name="{$origTypeGraphName}">
					<xsl:for-each select="graph[@id='Type Graph']/node">
						<Node ID="{generate-id(.)}" type="NT{generate-id(.)}">
							<NodeLayout X="{attr[@name='X']/.}" Y="{attr[@name='Y']/.}"/>
						</Node>
					</xsl:for-each>
					<xsl:for-each select="graph[@id='Type Graph']/edge">
						<xsl:variable name="from" select="@from"/>
						<xsl:variable name="to" select="@to"/>
						<!-- @id = EdgeID:EdgeName -->
						<xsl:variable name="id" select="substring-before(@id,':')"/>
						<xsl:variable name="name" select="substring-after(@id,':')"/>
						<!-- get ID from the first EdgeType with our TypeName -->
						<xsl:variable name="ETname" 
							select="/gxl/graph[@id='Type Graph']/edge[substring-after(@id,':')=$name]/@id"/>
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
			<!-- convert Instance Graph for AGG -->
			<xsl:variable name="origInstanceGraphName" 
				select="saxon:if(/gxl/graph[@id='Instance Graph']/attr,/gxl/graph[@id='Instance Graph']/attr/@name,'Instance Graph')"/>
			<xsl:variable name="origInstanceGraphID" 
				select="saxon:if(/gxl/graph[@id='Instance Graph']/attr,/gxl/graph[@id='Instance Graph']/attr/@id,'IDInstanceGraph')"/>
			<Graph ID="{$origInstanceGraphID}" name="{$origInstanceGraphName}">
				<xsl:apply-templates select="/gxl/graph[@id='Instance Graph']/node"/>
				<xsl:apply-templates select="/gxl/graph[@id='Instance Graph']/edge"/>
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
					<EdgeType ID="ET{generate-id(.)}" name="{@id}"/>
				</xsl:for-each>
			</Types>
			<!-- convert Instance Graph for AGG -->
			<xsl:variable name="origInstanceGraphName" 
				select="saxon:if(/gxl/graph[@id='Instance Graph']/attr,/gxl/graph[@id='Instance Graph']/attr/@name,'Instance Graph')"/>
			<xsl:variable name="origInstanceGraphID" 
				select="saxon:if(/gxl/graph[@id='Instance Graph']/attr,/gxl/graph[@id='Instance Graph']/attr/@id,'IDInstanceGraph')"/>
			<Graph ID="{$origInstanceGraphID}" name="{$origInstanceGraphName}">
				<xsl:apply-templates select="/gxl/graph[@id='Instance Graph']/node"/>
				<xsl:apply-templates select="/gxl/graph[@id='Instance Graph']/edge"/>
			</Graph>
		</xsl:if> 
		<!-- CASE 3: GXL-File has no Type Definitions -->
		<xsl:if test="not ( (graph/@id='Type Graph') or (graph/@id='Schema Graph') )">
			<!-- BEGIN: fujaba_gxl2ggx.xsl - Code -->
			<Types>
				<xsl:for-each select="graph/node/type">
					<xsl:variable name="href" select="../type/@xlink:href"/>
					<xsl:variable name="test1" select="generate-id()"/>
					<xsl:variable name="test2" select="generate-id(/gxl/graph/node/type[@xlink:href=$href])"/>
					<!-- convert NodeTypes with the same name only ones -->
					<xsl:if test="$test1=$test2">
						<NodeType ID="NT{../@id}" name="{$href}">
							<xsl:for-each select="../attr">
								<xsl:variable name="typename" select="name(*)"/>
								<!-- Fujaba 'seq' => Java Vector -->
								<xsl:if test="$typename='seq'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}#{position()}" 
										typename="Vector"/>
								</xsl:if>
								<xsl:if test="$typename='string'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}#{position()}" 
										typename="String"/>
								</xsl:if>
								<xsl:if test="$typename!='seq' and $typename!='string'">
									<AttrType ID="NT{../@id}AT{position()}" 
										attrname="{@name}#{position()}" 
										typename="{name(*)}"/>
								</xsl:if>
							</xsl:for-each>
						</NodeType>
					</xsl:if>
				</xsl:for-each>

				<EdgeType ID="ETdummy" name=""/>
			</Types>
			<!-- Fujaba Graph => AGG Instance Graph -->
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
								<xsl:if test="name(*)='string'">
									<Attribute constant="true" type="NT{$NTid}AT{$pos}">
										<Value>
											<xsl:element name="{name(*)}">
												<xsl:value-of select="*"/>
											</xsl:element>
										</Value>
									</Attribute>
								</xsl:if>
								<xsl:if test="name(*)!='seq' and name(*)!='string'">
									<Attribute type="NT{$href}AT{$pos}" value="{*}"/>
								</xsl:if>
							</xsl:for-each>
						</Node>
				</xsl:for-each>

				<xsl:for-each select="graph/edge">
					<Edge ID="EG{position()}" source="{@from}" target="{@to}" type="ETdummy">	
					</Edge>
				</xsl:for-each>

			</Graph>
			<!-- END: fujaba_gxl2ggx.xsl - Code -->
		</xsl:if>
  	   </GraphTransformationSystem>
	</Document>
</xsl:template>

<xsl:template match="node">
	<!-- convert 'node' in 'Instance Graph' -->
	<xsl:if test="/gxl/graph[@id='Type Graph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated NodeType-ID -->
		<xsl:variable name="NTid" select="generate-id(/gxl/graph[@id='Type Graph']/node[@id=$href])"/>
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

<xsl:template match="attr">
	<!-- convert 'attr' in 'Instance Graph' -->
	<xsl:if test="not (@kind='Layout')">
		<xsl:variable name="name" select="@name"/>
		<xsl:variable name="href" select="../type/@xlink:href"/>
		<!-- get the generated AttrType-ID -->
		<xsl:variable name="ATid" select="saxon:if(/gxl/graph/@id='Type Graph',generate-id(/gxl/graph[@id='Type Graph']/node[@id=$href]/attr[@name=$name]),generate-id(/gxl/graph[@id='Schema Graph']/node[@id='X']/graph[@id='X_Graph']/node[@id=$href]/attr[@name=$name]))"/>
		<Attribute constant="true" type="AT{$ATid}">
			<xsl:if test="locator/java"> 
				<Value>
					<xsl:copy-of select="locator/java"/>
				</Value>
			</xsl:if>
			<xsl:if test="locator/object"> 
				<Value>
					<xsl:copy-of select="locator/object"/>
				</Value>
			</xsl:if>
			<xsl:if test="bool"> 
				<Value>
					<boolean>
						<xsl:value-of select="."/>
					</boolean>
				</Value>
			</xsl:if>
			<xsl:if test="int | string | float"> 
				<Value>
					<xsl:copy-of select="int | string | float"/>
				</Value>
			</xsl:if>
		</Attribute>
	</xsl:if>
</xsl:template>

<xsl:template match="edge">
	<!-- convert 'edge' in 'Instance Graph' -->
	<xsl:if test="/gxl/graph[@id='Type Graph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated EdgeType-ID -->
		<xsl:variable name="id" select="substring-before($href,':')"/>
		<xsl:variable name="name" select="substring-after($href,':')"/>
		<!-- get ID from the first EdgeType with our TypeName -->
		<xsl:variable name="ETname" 
			select="/gxl/graph[@id='Type Graph']/edge[substring-after(@id,':')=$name]/@id"/>
		<xsl:variable name="ETid" select="substring-before($ETname,':')"/>
		<Edge ID="{@id}" source="{@from}" target="{@to}" type="ET{$ETid}">
			<EdgeLayout bendX="{attr[@name='bendX']}" bendY="{attr[@name='bendY']}"
				textOffsetX="{attr[@name='textOffsetX']}" textOffsetY="{attr[@name='textOffsetY']}"/>
		</Edge>
	</xsl:if>
	<xsl:if test="/gxl/graph[@id='Schema Graph']">
		<xsl:variable name="href" select="type/@xlink:href"/>
		<!-- get the generated EdgeType-ID -->
		<xsl:variable name="ETid" select="generate-id(/gxl/graph[@id='Schema Graph']/edge[@id=$href])"/>
		<Edge ID="{@id}" source="{@from}" target="{@to}" type="ET{$ETid}">
			<EdgeLayout bendX="{attr[@name='bendX']}" bendY="{attr[@name='bendY']}"
				textOffsetX="{attr[@name='textOffsetX']}" textOffsetY="{attr[@name='textOffsetY']}"/>
		</Edge>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>

