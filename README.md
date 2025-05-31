Untuk folder native, isi sama libopencv_java4120.so sama libtensorflow_jni.so

Untuk cara dapet libopencv_java4120.so cari sendiri aja,

Dan unduh [libtensorflow_jni.so]([url](https://www.tensorflow.org/install/lang_java_legacy)) (cari dipaling bawah, sesuaiin sama OS nya).

Nanti import .jar nya sebagai library, terus library tersebut set native library location nya ke arah /parke-db/native/xxx.so 

untuk resources/model --> isi sama [pre-trained model](https://drive.usercontent.google.com/download?id=1R77HmFADxe87GmoLwzfgMu_HY0IhcyBz&export=download&authuser=0&confirm=t&uuid=011d4429-01a7-4dd8-ae94-7da2a77a3f1a&at=ALoNOgkZYvrWtOLEWDUKZ4o-wekF%3A1748674658925) , rename file tersebut jadi facenet.pb

database sesuaiin sama dbml yang ada di repo ini (pastiin PK sama FK nya bener)
