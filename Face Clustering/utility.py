import re

def get_id(ipstring):
    ipstring=ipstring[1:]
    out=re.search("[^\.]*\.",ipstring)
    # print(out)
    return ipstring[out.regs[0][0]:out.regs[0][1]-1]
