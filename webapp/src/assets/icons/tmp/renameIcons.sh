for f in $(ls *x*.png);do SIZE=$(echo "$f" | sed 's/^.*x//g' | sed 's/\..*$//g'); mv -f $f ./../icon-${SIZE}x${SIZE}.png ; done;
