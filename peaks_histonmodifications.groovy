
def file = new File("Data/BroadHistone_K562_ControlStdAln_Rep1.sam")
String annotationFile = "test.bed"//"./shortTssAnnotationhg19.bed"
String samFile = "test.sam"

keyList = []

annotationMap = [:]
def samMap = [:]
cnt = 0
annotationMap = readAnnotationFiles(annotationFile)


annotationMap.keySet().each{
	keyList.add(it)
	annotationMap.get(it).sort()
	println "${it} | Size = ${annotationMap.get(it).size()}"
}


readSamFiles(samFile)

def readSamFiles(String path){
	def map = [:]
	def list = []
	def file = new File(path)
	def cnt = 0
	def tmp = annotationMap.get(keyList[cnt])
	println "tmp: ${tmp}"
	def j = 0
	def hits = 0

	file.each{
		def (value1, value2) = it.tokenize( ' ' )
		println "value1 = ${value1} | ${value1.getClass()} & keyList[cnt] = ${keyList[cnt]} | ${keyList[cnt].getClass()}"
		if(value1 == keyList[cnt]){
			println "hey"
			while(j < tmp.size() && value2 < tmp[j]-1000){
				j = j+1
			}
			while(j < tmp.size() && value2 < tmp[j]+1000){
				hits = hits + 1
				j = j+1
			}
		} else {
			cnt++
			tmp = keyList[cnt]
		}
	}
	println "Hits: ${hits}"
}

def readAnnotationFiles(String path){
	def map = [:]
	def list = []
	def file = new File(path)
	def tmp = "chr1"

	file.each{
		def (value1, value2) = it.tokenize('\t')
		if(value1!=tmp){
			tmp = value1
			list = []
		}

		list.add(value2.toInteger())
		map.put(tmp, list)
	}
	return map
}




println annotationMap.get("chr1 ")