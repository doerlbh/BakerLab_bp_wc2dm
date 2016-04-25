#!/bin/bash

# maximum jobs allowed at a time
MAXJOBS=20

j=1
for i in *.bp;
do
  activeJobs=$(ps -U sunnylin | grep -c remodel)
  while [ $activeJobs -ge $MAXJOBS ]
  do
    # wait for 1 minutes;
    # can change to seconds by removing "m"
    sleep 20s
    activeJobs=$(ps -U sunnylin | grep -c remodel)
    echo $activeJobs
  done #end_of_while_loop
  
  mkdir $j-$i;
  echo $i;
  mv $i $j-$i;
  cd $j-$i
  #copy run.sh you want
  #cp ~/bash_scripts/run.sh .;
  cp /work/sunnylin/domain_swap_heterodimer/whole_complex/run_WC.sh .;
  cp ../joint_renum.pdb .;
  cp ../run_WC.sh .;
  cp /work/sunnylin/domain_swap_heterodimer/whole_complex/cst .;
  #execute run.sh on file $i
  chmod +x run_WC.sh;
  ./run_WC.sh;
  cd ../;
  let j=j+1;
done