mvn heroku:deploy

echo "Starting web..."
heroku ps:scale -a fitme-app web 1
