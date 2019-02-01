import os
import mysql.connector
from align_faces import get_aligned_faces
from cluster_faces import cluster_face_images
from download_files import download_data
from utility import get_id


DATAFILE="face_data/"
DIRECTORY=os.getcwd()+'/'+DATAFILE
FOUND='f'
MISSING='m'



def update_db():
    cnx = mysql.connector.connect(user='meraki@meraki', password='codefundo@123',
                                      host='meraki.mysql.database.azure.com',
                                      database='meraki')
    cursor = cnx.cursor(buffered=True)

    faces=[]
    face_id=[]
    for filename in os.listdir(DIRECTORY):
        aligned_face=get_aligned_faces(DIRECTORY+filename)
        if(type(aligned_face)==type(str)):
            print("Face not Detected")
        else:
            if type(aligned_face)==None:
                continue
            faces.append(aligned_face)
            face_id.append(filename)

    labels = cluster_face_images(faces)
    print(labels,face_id)
    matched_dict={}
    matched_cluster_id={}
    for iter,face_id_ in enumerate(face_id):
        actual_face_id=get_id(face_id_)
        if face_id_.startswith(FOUND):
            if labels[iter] in matched_cluster_id:
                matched_cluster_id[labels[iter]].add(actual_face_id)
            else:
                matched_cluster_id[labels[iter]]={actual_face_id}

    label_cs_id={}
    for label in matched_cluster_id:
        cs_ids=""
        for cluster_face_id in matched_cluster_id[label]:
            cs_ids+=cluster_face_id + ','
        cs_ids=cs_ids[:-1]
        label_cs_id[label]=cs_ids



    for iter,face_id_ in enumerate(face_id):
        if face_id_.startswith(MISSING):
            di = get_id(face_id_)
            if labels[iter] in label_cs_id:
                query = (
                    "SELECT id, found_id FROM matched_people"
                    " WHERE id = %s")

                cursor.execute(query, (di,))
                victim_id,found_id_ = cursor.fetchone()
                if found_id_== None:
                    updated_found_id=label_cs_id[labels[iter]]
                else:
                    found_set=set(found_id_.split(','))
                    found_set=found_set | set(label_cs_id[labels[iter]].split(','))
                    updated_found_id=','.join(found_set)
                if updated_found_id.startswith(','):
                    updated_found_id=updated_found_id[1:]
                print(updated_found_id)
                sql="update meraki.matched_people set isfound= 1,found_id='%s' where id='%s'"%\
                     (updated_found_id,di)

                number_of_rows = cursor.execute(sql)
                cnx.commit()
            else:
                print("Not found")

if __name__=="__main__":
    download_data()
    update_db()
