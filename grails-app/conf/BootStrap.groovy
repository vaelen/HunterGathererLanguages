// Written by Andrew Young.

class BootStrap {
     def init = { servletContext ->
         def caseStudyRegions = CaseStudyRegion.list()
         if(!caseStudyRegions) {
             new CaseStudyRegion(name:"All").save()
             new CaseStudyRegion(name:"Australia").save()
             new CaseStudyRegion(name:"North America").save()
             new CaseStudyRegion(name:"Amazonia").save()
         }
         def partsOfSpeech = PartOfSpeech.list()
         if(!partsOfSpeech) {
             new PartOfSpeech(name:"Unknown").save()
             new PartOfSpeech(name:"Noun").save()
             new PartOfSpeech(name:"Verb").save()
             new PartOfSpeech(name:"Adjective").save()
             new PartOfSpeech(name:"Adverb").save()
             new PartOfSpeech(name:"Determiner").save()
             new PartOfSpeech(name:"Case Marking").save()
             new PartOfSpeech(name:"Particle").save()
         }
         def exportSets = ExportSet.list()
         if(!exportSets) {
             new ExportSet(name:"None").save()
             new ExportSet(name:"Widely Attested").save()
             new ExportSet(name:"Highly Borrowed").save()
         }
         def english = SourceLanguage.findByName("English")
         if(!english) {
             def regionAll = CaseStudyRegion.findByName("All")
             new SourceLanguage(name:"English", family:"Indo-European", subGroup:"Germanic", isoCode:"eng", caseStudyRegion: regionAll).save()
         }
     }
     def destroy = {
     }
} 
