create table qreceipt_receipt_form
(
    id          bigserial primary key,
    description varchar(255)
);

create table qreceipt_receipt_template
(
    id       bigserial primary key,
    value    text,
    position int,
    form_id  bigint references qreceipt_receipt_form (id)
);

create table qreceipt_template_param
(
    id          bigserial primary key,
    name        varchar(255),
    template_id bigint references qreceipt_receipt_template (id)
);

INSERT INTO public.qreceipt_receipt_form (description)
VALUES ('test');

INSERT INTO public.qreceipt_receipt_template (form_id, position, value)
VALUES (1, 1, '<?xml version="1.0" encoding="UTF-8"?>
<!-- Created with Jaspersoft Studio version 6.17.0.final using JasperReports Library version 6.17.0-6d93193241dd8cc42629e188b94f9e0bc5722efd  -->
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="acts" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="ecd136ea-cc18-4b4b-864f-84950a239165">
	<property name="com.jaspersoft.studio.data.defaultdataadapter" value="One Empty Record"/>
	<style name="Table_TH" mode="Opaque" backcolor="#F0F8FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table_CH" mode="Opaque" backcolor="#BFE1FF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<style name="Table_TD" mode="Opaque" backcolor="#FFFFFF">
		<box>
			<pen lineWidth="0.5" lineColor="#000000"/>
			<topPen lineWidth="0.5" lineColor="#000000"/>
			<leftPen lineWidth="0.5" lineColor="#000000"/>
			<bottomPen lineWidth="0.5" lineColor="#000000"/>
			<rightPen lineWidth="0.5" lineColor="#000000"/>
		</box>
	</style>
	<subDataset name="MyDataSet" uuid="9f0cd1ee-5b04-4a8f-be07-64c1533559f6">
		<queryString>
			<![CDATA[]]>
		</queryString>
		<field name="rowNumber" class="java.lang.String"/>
		<field name="key" class="java.lang.String"/>
		<field name="value" class="java.lang.String"/>
	</subDataset>
	<parameter name="main" class="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"/>
	<queryString>
		<![CDATA[]]>
	</queryString>
	<background>
		<band splitType="Stretch"/>
	</background>
	<detail>
		<band height="702" splitType="Stretch">
			<componentElement>
				<reportElement x="2" y="150" width="550" height="410" uuid="40438c6f-c510-446a-874d-a508812971d0">
					<property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.VerticalRowLayout"/>
					<property name="com.jaspersoft.studio.table.style.table_header" value="Table_TH"/>
					<property name="com.jaspersoft.studio.table.style.column_header" value="Table_CH"/>
					<property name="com.jaspersoft.studio.table.style.detail" value="Table_TD"/>
				</reportElement>
				<jr:table xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd">
					<datasetRun subDataset="MyDataSet" uuid="45371799-3919-41c3-a7d9-2193cc00982c">
						<dataSourceExpression><![CDATA[$P{main}]]></dataSourceExpression>
					</datasetRun>
					<jr:column width="30" uuid="a2036619-98c6-4958-85dd-86a007b68b4c">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Column1"/>
						<jr:columnHeader style="Table_CH" height="40" rowSpan="1">
							<textField>
								<reportElement x="0" y="0" width="30" height="40" uuid="91718777-0b25-4918-92ef-2b569ab88ec6"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14" isBold="true"/>
								</textElement>
								<textFieldExpression><![CDATA["№"]]></textFieldExpression>
							</textField>
						</jr:columnHeader>
						<jr:detailCell style="Table_TD" height="30">
							<textField>
								<reportElement x="0" y="0" width="30" height="30" uuid="82801670-ad87-481d-aebc-b9a53b09cc6a"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{rowNumber}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="240" uuid="4da97d26-5894-4365-a44e-237b3ede467f">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Column2"/>
						<jr:columnHeader style="Table_CH" height="40" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="240" height="40" uuid="238bf08f-9a80-4214-ba60-6bb0c70ee72d"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14" isBold="true"/>
								</textElement>
								<text><![CDATA[Key]]></text>
							</staticText>
						</jr:columnHeader>
						<jr:detailCell style="Table_TD" height="30">
							<textField>
								<reportElement x="0" y="0" width="240" height="30" uuid="37b24b55-aadb-461d-bfee-0c0486c749f1"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{key}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
					<jr:column width="240" uuid="a45184f5-11cd-4f09-b1c1-70bffd6afef3">
						<property name="com.jaspersoft.studio.components.table.model.column.name" value="Column3"/>
						<jr:columnHeader style="Table_CH" height="40" rowSpan="1">
							<staticText>
								<reportElement x="0" y="0" width="240" height="40" uuid="46848d16-9abe-4327-a0cd-f6b29805491e"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14" isBold="true"/>
								</textElement>
								<text><![CDATA[Value]]></text>
							</staticText>
						</jr:columnHeader>
						<jr:detailCell style="Table_TD" height="30">
							<textField>
								<reportElement x="0" y="0" width="240" height="30" uuid="f3aa5a6d-50e2-4094-a07c-b10ef2c08555"/>
								<textElement textAlignment="Center">
									<font fontName="Times New Roman" size="14"/>
								</textElement>
								<textFieldExpression><![CDATA[$F{value}]]></textFieldExpression>
							</textField>
						</jr:detailCell>
					</jr:column>
				</jr:table>
			</componentElement>
			<textField>
				<reportElement x="230" y="110" width="90" height="20" uuid="258b611a-1e20-4179-a485-d2c31731d972"/>
				<textElement>
					<font fontName="Times New Roman" size="14" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["QRECEIPT"]]></textFieldExpression>
			</textField>
		</band>
	</detail>
	<columnFooter>
		<band height="50">
			<textField>
				<reportElement x="30" y="30" width="300" height="20" uuid="0c52413f-ffb2-40cb-97ed-efd7a5e53945"/>
				<textElement>
					<font fontName="Times New Roman" size="14"/>
				</textElement>
				<textFieldExpression><![CDATA["With Respect, QRecipient"]]></textFieldExpression>
			</textField>
		</band>
	</columnFooter>
	<pageFooter>
		<band height="50"/>
	</pageFooter>
</jasperReport>
');

INSERT INTO public.qreceipt_template_param (name, template_id)
VALUES ('main', 1);
