<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><g:layoutTitle default="Grails" /> - Hunter Gatherer Languages Database</title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
        <export:resource />
        <!-- YUI Section -->
        <!-- Fonts CSS - Recommended but not required -->
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/fonts/fonts-min.css">

        <!-- Core + Skin CSS -->
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/menu/assets/skins/sam/menu.css">

        <!-- Dependencies -->
        <script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/container/container_core-min.js"></script>

        <!-- Source File -->
        <script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/menu/menu-min.js"></script>

        <script type="text/javascript">
          // Initialize and render the MenuBar when it is available in the page

          YAHOO.util.Event.onContentReady("site-nav", function () {

	    /*
	         Instantiate a MenuBar.  The first argument passed to the
	         constructor is the id of the element in the page that
	         represents the MenuBar; the second is an object literal
	         representing a set of configuration properties.
	    */

	    var oMenuBar = new YAHOO.widget.MenuBar("site-nav", {
	                                                autosubmenudisplay: true,
	                                                hidedelay: 750,
	                                                lazyload: true });


	    /*
	         Call the "render" method with no arguments since the
	         markup for this MenuBar already exists in the page.
	    */

	    oMenuBar.render();

	  });
        </script>
        <!-- End YUI Section -->
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images/logos',file:'spinner.gif')}" alt="Spinner" />
        </div>	
        <%-- <div class="logo"><img src="${resource(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div> --%>
        <div id="site-nav-wrapper" class="yui-skin-sam" style="margin-bottom: 1em;">
          <div id="site-nav" class="yuimenubar yuimenubarnav">
            <div class="bd">
              <ul class="first-of-type">
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#general">General</a>
                  <div id="site-nav-general" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="sourceLanguage">Languages</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="source">Sources</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#demographic">Demographic</a>
                  <div id="site-nav-demographic" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="demographicData">Data</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="demographicFeature">Features</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="demographicFeatureCategory">Feature Categories</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#ethnographic">Ethnographic</a>
                  <div id="site-nav-ethnographic" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="ethnographicData">Data</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="ethnographicFeature">Features</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="ethnographicFeatureCategory">Feature Categories</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#genetic">Genetic</a>
                  <div id="site-nav-genetic" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="geneticData">Data</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="geneticFeature">Features</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="geneticFeatureCategory">Feature Categories</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#grammatical">Grammatical</a>
                  <div id="site-nav-grammatical" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="grammaticalData">Data</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="grammaticalFeature">Features</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="grammaticalFeatureCategory">Feature Categories</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#lexical">Lexical</a>
                  <div id="site-nav-lexical" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="lexicalData">Data</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="lexicalFeature">Features</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="lexicalFeatureCategory">Feature Categories</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="reconstruction">Reconstructions</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="reconstructionData">Reconstruction Data</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#settings">Settings</a>
                  <div id="site-nav-settings" class="yuimenu">
                    <div class="bd">
                      <ul>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="semanticField">Semantic Fields</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="caseStudyRegion">Case Study Regions</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="exportSet">Export Sets</g:link></li>
                        <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="partOfSpeech">Parts of Speech</g:link></li>
                      </ul>
                    </div>
                  </div>
                </li>
                <n:notUser>
                  <li class="yuimenubaritem first-of-type"><g:link class="yuimenubaritemlabel" controller="auth" action="login">Login</g:link></li>
                </n:notUser>
                <n:user>
                  <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#entry">Data Editing</a>
                    <div id="site-nav-settings" class="yuimenu">
                      <div class="bd">
                        <ul>
                          <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="editCognacy">Do Cognacy Judgements</g:link></li>
                        </ul>
                      </div>
                    </div>
                  </li>
                </n:user>
                <n:user>
                  <li class="yuimenubaritem first-of-type"><a class="yuimenubaritemlabel" href="#account">Account</a>
                    <div id="site-nav-account" class="yuimenu">
                      <div class="bd">
                        <ul>
                          <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="profile">Profile</g:link></li>
                          <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="auth" action="logout">Logout</g:link></li>
                          <n:isAdministrator>
                            <li class="yuimenuitem first-of-type"><a class="yuimenuitemlabel" href="#administration">Administration</a>
                              <div id="site-nav-administration" class="yuimenu">
                                <div class="bd">
                                  <ul>
                                    <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="admins">Admins</g:link></li>
                                    <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="user">Users</g:link></li>
                                    <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="group">Groups</g:link></li>
                                    <li class="yuimenuitem"><g:link class="yuimenuitemlabel" controller="role">Roles</g:link></li>
                                  </ul>
                                </div>
                              </div>
                            </li>
                          </n:isAdministrator>
                        </ul>
                      </div>
                    </div>
                  </li>
                </n:user>
              </ul>
            </div>
          </div>
        </div>
        <g:layoutBody />
    <br clear="all"/>
        <div class="disclaimer">
          <table border="0" style="border:0px">
            <tr>
              <td valign="middle"><img src="${resource(dir:'images/logos',file:'nsf-100.jpg')}"/></td>
              <td valign="middle" style="size: 50%">
                This database has been developed as part of NSF grant BCS-902114
                “Dynamics of Hunter-Gatherer Language Change”, awarded to Yale
                University. PIs are Claire Bowern (Yale University), Patience Epps (UT
                Austin), Jane Hill (Arizona), and Keith Hunley (New Mexico); Patrick
                McConvell (ANU), Jack Ives (Alberta) and Russell Gray (Auckland) are
                also affiliated. The content and opinions expressed here are solely
                those of the writers and are not necessarily consistent with the
                policies or opinions of the NSF.<br/>
                <br/>
                © 2010 This material is copyright. Please do not cite or publish
                anything in this database without permission from the authors.<br/>
                <br/>
                For comments, corrections and further information, please contact
                Claire Bowern (<a href="mailto:claire.bowern@yale.edu">claire.bowern@yale.edu</a>).<br/>
              </td>
            </tr>
          </table>
        </div>
    </body>	
</html>
