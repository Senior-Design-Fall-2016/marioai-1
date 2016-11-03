for i in gui/Generation.java; do
iconv -f utf-8 -t utf-8 -c $i > $i
done
