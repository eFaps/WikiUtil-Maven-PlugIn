/*
 * Copyright 2003 - 2010 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */

package org.efaps.wikiutil.maven;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.efaps.maven_java5.org.apache.maven.tools.plugin.Goal;
import org.efaps.maven_java5.org.apache.maven.tools.plugin.Parameter;
import org.efaps.wikiutil.export.latex.MakePDF;
import org.efaps.wikiutil.parser.gwiki.javacc.ParseException;

/**
 * Mojo to convert Wiki pages to PDF.
 *
 * @author The eFaps Team
 * @version $Id$
 */
@Goal(name = "convert2pdf")
public class ConvertToPDFMojo
    extends AbstractMojo
{
    /**
     * Source directory where the Wiki pages are located.
     */
    @Parameter(required = true, expression = "${basedir}/src/documentation/wiki")
    private File sourceDir;

    /**
     * Target file directory where the Latex files are created.
     */
    @Parameter(required = true, expression = "${project.build.directory}/wiki2pdf")
    private File targetDir;

    /**
     * Target PDF file.
     */
    @Parameter(required = true, expression = "${project.build.directory}/${project.artifactId}-${project.version}.pdf")
    private File targetFile;

    /**
     * File extension of the Wiki pages.
     */
    @Parameter(defaultValue = ".wiki")
    private String fileExtension;

    /**
     * Name of the index wiki page.
     */
    @Parameter(defaultValue = "Index")
    private String indexName;

    /**
     * Author of the converted document.
     */
    @Parameter(expression = "${user.name}")
    private String wikiAuthor;

    /**
     * Version used for the Wiki.
     */
    @Parameter(expression = "${project.version}")
    private String wikiVersion;

    /**
     * Name of the license.
     */
    @Parameter
    private String wikiLicense;

    /**
     * File of the license text.
     */
    @Parameter
    private File wikiLicenseFile;

    /**
     * Title of the PDF document.
     */
    @Parameter(expression = "${project.name}")
    private String wikiTitle;

    /**
     * Sub title of the PDF document.
     */
    @Parameter
    private String wikiSubTitle;

    /**
     * Creator for the PDF property.
     */
    @Parameter(defaultValue = "eFaps Wiki Util")
    private String wikiPDFCreator;

    /**
     * Keywords for the PDF property.
     */
    @Parameter
    private String wikiPDFKeywords;

    /**
     * Executes the convert of Wiki pages to a PDF file.
     *
     * @throws MojoExecutionException if PDF file could not be created
     */
    @Override
    public void execute()
        throws MojoExecutionException
    {
        try {
            final MakePDF makePDF = new MakePDF(this.targetDir)
                .setWikiFileExtension(this.fileExtension)
                .setWikiIndexName(this.indexName)
                .setWikiRootURI(this.sourceDir.toURI())
                .setWikiTargetFile(this.targetFile);

            if ((this.wikiAuthor != null) && !"".equals(this.wikiAuthor))  {
                makePDF.variable("WikiAuthor", this.wikiAuthor);
            }
            if ((this.wikiVersion != null) && !"".equals(this.wikiVersion))  {
                makePDF.variable("WikiVersion", this.wikiVersion);
            }
            if ((this.wikiLicense != null) && !"".equals(this.wikiLicense))  {
                makePDF.variable("WikiLicense", this.wikiLicense);
            }
            if (this.wikiLicenseFile != null)  {
                makePDF.variable("WikiLicenseFile", this.wikiLicenseFile.toString());
            }
            if ((this.wikiTitle != null) && !"".equals(this.wikiTitle))  {
                makePDF.variable("WikiTitle", this.wikiTitle);
            }
            if ((this.wikiSubTitle != null) && !"".equals(this.wikiSubTitle))  {
                makePDF.variable("WikiSubTitle", this.wikiSubTitle);
            }
            if ((this.wikiPDFCreator != null) && !"".equals(this.wikiPDFCreator))  {
                makePDF.variable("WikiPDFCreator", this.wikiPDFCreator);
            }
            if ((this.wikiPDFKeywords != null) && !"".equals(this.wikiPDFKeywords))  {
                makePDF.variable("WikiPDFKeywords", this.wikiPDFKeywords);
            }

            makePDF.execute();
        } catch (final IOException e) {
            throw new MojoExecutionException("convert failed", e);
        } catch (final ParseException e) {
            throw new MojoExecutionException("convert failed", e);
        }
    }
}
