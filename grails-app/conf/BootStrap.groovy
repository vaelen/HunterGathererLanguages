class BootStrap {

     def init = { servletContext ->
         new CaseStudyRegion(name:"Australia").save()
         new CaseStudyRegion(name:"North America").save()
         new CaseStudyRegion(name:"Amazonia").save()
         new PartOfSpeech(name:"Noun").save()
         new PartOfSpeech(name:"Verb").save()
         new PartOfSpeech(name:"Adjective").save()
         new PartOfSpeech(name:"Adverb").save()
         new PartOfSpeech(name:"Determiner").save()
         new PartOfSpeech(name:"Case Marking").save()
         new PartOfSpeech(name:"Particle").save()
         new ExportSet(name:"Widely Attested").save()
         new ExportSet(name:"Highly Borrowed").save()
     }
     def destroy = {
     }
} 