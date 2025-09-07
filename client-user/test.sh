#!/bin/bash
for (( i = 0 ; i <= 20 ; i += 1 )) ; do
  curl http://localhost:8080/v1/mail/track-package?packageNumber=1 &
done