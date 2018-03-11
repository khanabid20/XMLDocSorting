<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" />
	<!-- <xsl:strip-space elements="*" /> -->
	
	<xsl:template match="Persons">
		<xsl:element name="Persons" xmlns:m="http://www.w3.org/1999/XSL/Transform">
			<xsl:for-each select="Person">
				<xsl:sort select="m:name"/>
				<Person>
					<xsl:text disable-output-escaping="yes">&lt;m:name&gt;</xsl:text><xsl:value-of select="m:name"/><xsl:text disable-output-escaping="yes">&lt;/m:name&gt;</xsl:text>
					<age><xsl:value-of select="age"/></age>
					<xsl:for-each select="m:email">
						<xsl:sort select="."/>
						<xsl:text disable-output-escaping="yes">&lt;m:email&gt;</xsl:text><xsl:value-of select="."/><xsl:text disable-output-escaping="yes">&lt;/m:email&gt;</xsl:text>
					</xsl:for-each>
				</Person>
			</xsl:for-each>
			<!-- <xsl:copy-of select="work"/> -->
			
			<xsl:for-each select="work">
				<xsl:sort select="@location"/>
				<work location="{@location}">
					<xsl:for-each select="company">
						<xsl:sort select="@rank"/>
						<company rank="{@rank}"><xsl:value-of select="."/></company>
					</xsl:for-each>
				</work>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>