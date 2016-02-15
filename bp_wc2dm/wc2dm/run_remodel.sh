#!/bin/bash

#Command to run remodel, yeilding 5 output structures 

# OUTPUT - 1.pdb, 2.pdb .... etc yeilding different results from remodel
# INPUT - None given, requires one pdb and one bp in the folder

~possu/src/git/Rosetta/main/source/bin/remodel.static.linuxgccrelease -s *.pdb -remodel:blueprint *.bp @/work/sunnylin/scripts/flags_remodel_inputsc -chain A > log &