echo "Downloading code to: $PWD"

# Get Code
git clone https://github.com/jhaprabhatt/student-enrolment.git
cd student_enrolment

# Build Code
./mvnw clean package

cd student_enrolment-rest
docker build -t jhaprabhatt/student_enrollment .

# Run Docker image
docker run -p 8080:8080 -t jhaprabhatt/student_enrollment

