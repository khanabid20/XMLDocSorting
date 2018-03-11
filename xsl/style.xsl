<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" encoding="utf-8" indent="yes" version="1.0" />

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="Persons">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:apply-templates select="Person">
				<xsl:sort select="m:name"/>
			</xsl:apply-templates>
			<xsl:apply-templates select="work">
				<xsl:sort select="@location" data-type="text" order="ascending"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Person">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates select="m:name"/>
			<xsl:apply-templates select="age"/>
			<xsl:apply-templates select="m:email">
				<xsl:sort select="." data-type="text" order="ascending" />
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="work">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:apply-templates select="company">
				<xsl:sort select="@rank" data-type="number"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>