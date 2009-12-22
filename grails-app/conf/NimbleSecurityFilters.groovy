/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2009 Intient Pty Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import intient.nimble.service.AdminsService

/**
 * Filter that works with Nimble security model to protect controllers, actions, views
 *
 * @author Bradley Beddoes
 */
public class NimbleSecurityFilters extends intient.nimble.security.NimbleFilterBase {

    def CONTRIBUTOR_ROLE = "Contributor"

    def filters = {

        // Content requiring users to be authenticated
        secure(controller: "main") {
            before = {
                accessControl {
                    true
                }
            }
        }

        profilesecure(controller: "profile") {
            before = {
                if(!actionName.equals('miniprofile')) {
                    accessControl {
                        true
                    }
                }
            }
        }

        // Account management requiring authentication
        accountsecure(controller: "account", action: "(changepassword|updatepassword|changedpassword)") {
            before = {
                accessControl {
                    true
                }
            }
        }

        // This should be extended as the application adds more administrative functionality
        administration(controller: "(admins|user|group|role)") {
            before = {
                accessControl {
                    role(AdminsService.ADMIN_ROLE)
                }
            }
        }

        caseStudyRegionEditing(controller: "caseStudyRegion", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        demographicDataEditing(controller: "demographicData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        demographicFeatureCategoryEditing(controller: "demographicFeatureCategory", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        demographicFeatureEditing(controller: "demographicFeature", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        ethnographicDataEditing(controller: "ethnographicData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        ethnographicFeatureCategoryEditing(controller: "ethnographicFeatureCategory", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        ethnographicFeatureEditing(controller: "ethnographicFeature", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        exportSetEditing(controller: "exportSet", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        geneticDataEditing(controller: "geneticData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        geneticFeatureCategoryEditing(controller: "geneticFeatureCategory", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        geneticFeatureEditing(controller: "geneticFeature", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        grammaticalDataEditing(controller: "grammaticalData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        grammaticalFeatureCategoryEditing(controller: "grammaticalFeatureCategory", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        grammaticalFeatureEditing(controller: "grammaticalFeature", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        lexicalDataEditing(controller: "lexicalData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        lexicalFeatureCategoryEditing(controller: "lexicalFeatureCategory", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        lexicalFeatureEditing(controller: "lexicalFeature", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        partOfSpeechEditing(controller: "partOfSpeech", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        reconstructionDataEditing(controller: "reconstructionData", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        reconstructionEditing(controller: "reconstruction", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        semanticFieldEditing(controller: "semanticField", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        sourceEditing(controller: "source", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

        sourceLanguageEditing(controller: "sourceLanguage", action: "(create|edit|save|update|delete)") { 
            before = { 
                accessControl { 
                    role(CONTRIBUTOR_ROLE) 
                } 
            } 
        }

    }

}
