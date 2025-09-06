#!/bin/bash
for (( i = 0 ; i <= 20 ; i += 1 )) ; do
  java Client.java &
done