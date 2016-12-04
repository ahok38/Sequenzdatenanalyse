def file = new File("Data/BroadHistone_K562_ControlStdAln_Rep1.sam")
String annotationFile = "./shortTssAnnotationhg19.bed"//new File("./shortTssAnnotationhg19.bed")

def map = [:]
map = readAnnotationFiles(annotationFile)
	
//def (value1, value2) = "chr1	22231".tokenize( '\t' )

// Parser fuer Reads aus .sam Dateien
/*file2.eachLine{
	def (value1, value2) = it.tokenize( '\t' )
	if(value1!=tmp){
		tmp = value1
		list = []
	}

	list.add(value2.toInteger())
	map.put(tmp, list)
}*/

def readSamFiles(String path){

}

def readAnnotationFiles(String path){
	def map = [:]
	def list = []
	def file = new File(path)
	def tmp = "chr1"

	file.each{
		def (value1, value2) = it.tokenize( '\t' )
		if(value1!=tmp){
			tmp = value1
			list = []
		}

		list.add(value2.toInteger())
		map.put(tmp, list)
	}
	return map
}


map.keySet().each{
	println "${it} | Size = ${map.get(it).size()}"
}

