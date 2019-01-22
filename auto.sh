#! /bin/bash
APPLICATION_NAME=IndianPoker_Spring-Boot

SCRIPTPATH=$( cd "$(dirname "$0")" ; pwd -P )
echo "sh파일 실행 위치 $SCRIPTPATH"

echo "> Git pull"

git pull

echo "> Build project"

./gradlew build -x test

CURRENT_PID=$(pgrep -f indianpokerspring)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -2 $CURRENT_PID"
  kill -2 $CURRENT_PID
  sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls $SCRIPTPATH/build/libs |grep 'indianpokerspring' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

chmod 755 $SCRIPTPATH/build/libs/$JAR_NAME
nohup java -jar $SCRIPTPATH/build/libs/$JAR_NAME &