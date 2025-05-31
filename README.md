Untuk folder native, isi sama libopencv_java4120.so sama libtensorflow_jni.so

Untuk cara dapet libopencv_java4120.so cari sendiri aja,

Dan unduh [libtensorflow_jni.so](https://www.tensorflow.org/install/lang_java_legacy) (cari dipaling bawah, sesuaiin sama OS nya).

Nanti import .jar nya sebagai library, terus library tersebut set native library location nya ke arah /parke-db/native/xxx.so 

untuk resources/model --> isi sama [pre-trained model](https://drive.usercontent.google.com/download?id=1R77HmFADxe87GmoLwzfgMu_HY0IhcyBz&export=download&authuser=0&confirm=t&uuid=011d4429-01a7-4dd8-ae94-7da2a77a3f1a&at=ALoNOgkZYvrWtOLEWDUKZ4o-wekF%3A1748674658925) , rename file tersebut jadi facenet.pb

database sesuaiin sama dbml yang ada di repo ini (pastiin PK sama FK nya bener)

**UI OVERVIEW**
MainWindow (not updated yet!)
![image](https://github.com/user-attachments/assets/74e30889-39b1-454c-9a4e-1ab1916100b6)

EntryGate() (not updated yet!)
![image](https://github.com/user-attachments/assets/2507486a-17be-40d0-89c6-3b5a1068a7e1)

ExitGate() (not updated yet!)
![image](https://github.com/user-attachments/assets/38f54661-3759-44f8-a0fd-fde63b4d6e25)

RegistGate()
![image](https://github.com/user-attachments/assets/56f6a810-3666-4c63-8a80-cd71b94c7d61)

AddVehicle()
![image](https://github.com/user-attachments/assets/da0319f8-8c37-4b74-9b1f-e539a8037c75)

Root()
![image](https://github.com/user-attachments/assets/7e5e9b2d-5e78-4469-a135-c136872e6157)
![image](https://github.com/user-attachments/assets/a911396f-0f45-4c33-8639-4f5c06176c9f)
![image](https://github.com/user-attachments/assets/5324c858-eadc-4aeb-acf6-ec18bd06b0b7)

Camera
![image](https://github.com/user-attachments/assets/f55682f2-6012-4c2d-b9dd-398bb31d317b)

Success
![image](https://github.com/user-attachments/assets/bb7da2f4-4c54-49e0-abae-deb260bbb356)
![image](https://github.com/user-attachments/assets/5075df0c-887a-4081-afff-a8be35e88ccd)
