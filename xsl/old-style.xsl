<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://www.w3.org/1999/XSL/Transform;helllooooo">
	<xsl:output method="xml" indent="yes" />
	<xsl:strip-space elements="*" />
	
<xsl:variable name="rootChildOrder" select="'|Person|work|'"/>
<xsl:variable name="personChildOrder" select="'|m:name|age|m:email|'"/>

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="m:name[parent::Person]" xmlns="m=http://www.w3.org/1999/XSL/Transform;helllooooo">
				<xsl:sort select="." data-type="text"  order="ascending"/>
			</xsl:apply-templates>
			<xsl:apply-templates select="@*|node()">
				<xsl:sort select="substring-before($rootChildOrder, concat('|',name(),'|'))"/>
				<xsl:sort select="substring-before($personChildOrder, concat('|',name(),'|'))"/>
				<!-- <xsl:sort select="@id" data-type="number" order="ascending" /> -->
				<!-- <xsl:sort select="m:name" data-type="text" order="ascending" /> -->
				<!-- <xsl:sort select="m:name" data-type="text" order="ascending" /> -->
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="m:name[parent::Person]">
		<xsl:apply-templates select=".">
			<xsl:sort select="." data-type="text"  order="ascending"/>
		</xsl:apply-templates>
	</xsl:template>
<!-- 
	<xsl:template match="Person">
		<xsl:apply-templates>
			<xsl:sort select="m:name" data-type="text" order="ascending" />
		</xsl:apply-templates>
	</xsl:template> -->

<!-- 
	<xsl:template match="Person">
		<xsl:copy>
			<xsl:apply-templates select=".">
				<xsl:sort select="m:name" />
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
 -->
	<!-- <xsl:template match="work"> <xsl:copy> <xsl:apply-templates select="company"> 
		<xsl:sort select="text()" order="ascending"/> </xsl:apply-templates> </xsl:copy> 
		</xsl:template> -->

</xsl:stylesheet>