from azure.storage.file import FileService
import os
DATAFILE='face_data/'
SERVER_FOLDER='myshare'

def download_data():
    file_service = FileService(account_name='personimagedata', account_key='RNlmOs2+a0l0Vb2Ogxi3fSMA/LKsHgutsgvQE6p/Nu5OrXhmma8XNfnT3qFobkpGqYTecz75MXrmkoTThScJ+w==')
    generator = file_service.list_directories_and_files(SERVER_FOLDER)
    downloaded_files=set(filename for filename in os.listdir(os.getcwd()+'/'+DATAFILE) )
    for file_or_dir in generator:
        print(file_or_dir.name)
        if file_or_dir.name not in downloaded_files:
            file_service.get_file_to_path(SERVER_FOLDER, None, file_or_dir.name, DATAFILE+file_or_dir.name)

if __name__=="__main__":
    download_data()
