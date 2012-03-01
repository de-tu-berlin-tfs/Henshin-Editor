#!/bin/sh

for i in $*; do
	echo '// $Id: package.sh,v 1.2 2003/03/05 18:24:15 komm Exp $' > tmp
	echo "package agg.attribute.parser.javaExpr;" >> tmp
	cat $i >> tmp
	mv tmp $i
done
