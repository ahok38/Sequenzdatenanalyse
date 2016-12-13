
def file = new File("Data/BroadHistone_K562_ControlStdAln_Rep1.sam")
String annotationFile = "./shortTssAnnotationhg19_sorted.bed"
String samFile = "short2.sam"

hits = [:]
keyList = []
tmpHits = 0
totalHits = 0

annotationMap = [:]
def samMap = [:]
cnt = 0
annotationMap = readAnnotationFiles(annotationFile)


annotationMap.keySet().each{
	keyList.add(it)
	annotationMap.get(it).sort()
	println "${it} | Size = ${annotationMap.get(it).size()}"
}

println keyList

readSamFiles(samFile)

println hits
println "Total Hits: ${totalHits}"

def readSamFiles(String path){
	def map = [:]
	def list = []
	def file = new File(path)
	def cnt = 0
	def tmp = annotationMap.get(keyList[cnt])
	//println tmp
	//println "tmp: ${tmp}"
	def j = 0

	file.eachLine{
		def (value1, value2) = it.tokenize( ' ' )
		//println "value1 = ${value1} | keyList[cnt] = ${keyList[cnt]}"
		//println "value1 = ${value1} | ${value1.getClass()} & keyList[cnt] = ${keyList[cnt]} | ${keyList[cnt].getClass()}"
		//println "value2 = ${value2.toInteger()} | ${value2.toInteger().getClass()} & tmp = ${tmp[0]} | ${tmp[0].getClass()}"
		if(value1 == keyList[cnt]){
			value2 = value2.toInteger()
			/*while(j < tmp.size() && value2 < tmp[j]-1000){
				//j = j+1
				//println "Before ${value2}"
				return
				//println "Diag: chr = ${value1}|${keyList[cnt]} j = ${j} | tmp[{$j}] = ${tmp[j]} & ${tmp[j].getClass()} | tmp.size = ${tmp.size()}"
			}*/
			while(j < tmp.size() && value2 > tmp[j]+1000){
				j++
			}
			while(j < tmp.size() && value2 > tmp[j]-1000 && value2 < tmp[j]+1000){ // && value2 < tmp[j]+1000){
				//println "tmp = ${tmp}"
				//println "j = ${j} | tmp[j] = ${tmp[j]}"
				//println tmpHits
				//println "After: ${value2}"
				tmpHits = tmpHits + 1
				totalHits++
				//println "Hit: ${value1}"
				hits.put(value1, tmpHits)
				//println "Chromosom: ${keyList[cnt]} | Value2: ${value2} | tmp[${j}]: ${tmp[j]}"

				j = j+1
			}
		} else {
			//println "Before: value1 = ${value1} | keyList[${cnt}] = ${keyList[cnt]}"
			if(cnt < keyList.size()){
				cnt++
				tmp = annotationMap.get(keyList[cnt])
				//println "After: value1 = ${value1} | keyList[${cnt}] = ${keyList[cnt]}"
			}
			tmpHits = 0
			j = 0
		}
		
	}
	//println "Hits: ${hits}"
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




//println annotationMap.get("chr1")