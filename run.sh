echo "Downloading code to: $PWD"

# Get Code
git clone https://github.com/jhaprabhatt/student-enrolment.git
cd student-enrolment

# Build Code
./mvnw clean package

cd student-enrolment-rest
docker build -t jhaprabhatt/student_enrollment .

# Run Docker image
docker run -p 9090:9090 -t jhaprabhatt/student_enrollment

