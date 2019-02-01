import requests
from bs4 import BeautifulSoup
import datetime

class color:
 BOLD = '\033[1m'
 END = '\033[0m'

currentdatetime = datetime.datetime.now()

def crawler(url,divtype,classtype,otherdivtype,otherclass):
 sourcecode = requests.get(url).text
 soup = BeautifulSoup(sourcecode, "html.parser")

 for link in soup.findAll(divtype,{ "class": classtype}):
    print(color.BOLD + link.text + color.END)
    for item in link.findAll('a'):
        href = item.get('href')
        singleitem(href,otherdivtype,otherclass)

def singleitem(itemurl,otherdivtype,otherclass):
 sourcecode = requests.get(itemurl).text
 soup = BeautifulSoup(sourcecode, "html.parser")

 for link in soup.findAll(otherdivtype, {"id":otherclass}):
  print(link.text)



print("Update")

crawler("http://rsmcnewdelhi.imd.gov.in/index.php?lang=en","div","Scrollwarning","ul","ticker01")
