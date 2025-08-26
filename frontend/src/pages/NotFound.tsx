import { Link } from "react-router-dom";
import Container from "../components/Container";
import Heading1 from "../components/Heading1";

export default function NotFound() {
  return (
    <Container>
      <Heading1>Page Not Found</Heading1>
      <p>Sorry, the page you are looking for could not be found.</p>
      <Link to="/" className="inline-block rounded bg-black text-white px-4 py-2 hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black">Return to homepage</Link>
      <p>
        Or go to <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
      </p>
    </Container>
  );
}