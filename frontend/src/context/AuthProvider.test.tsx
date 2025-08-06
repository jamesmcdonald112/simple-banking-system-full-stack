import  { useAuth } from "./AuthContext";
import { beforeEach, describe, expect, test } from "vitest";
import { AuthProvider } from "./AuthProvider";
import { render, screen, act } from "@testing-library/react";

function TestComponent() {
  const {isLoggedIn, logIn, logOut} = useAuth();

  return (
    <div>
      <p data-testid="login-status">Logged in: {isLoggedIn.toString()}</p>
      <button onClick={logIn}>Log in</button>
      <button onClick={logOut}>Log out</button>
    </div>
  )
}

describe('AuthProvider with local storage', () => {
  beforeEach(() => {
    localStorage.clear();
  })


  test('provides default value', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )
    expect(screen.getByText(/logged in: false/i)).toBeInTheDocument()
  })

  test('allows updating login state', () => {
    render (
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    act(() => {
      screen.getByRole('button', {name: /log in/i}).click();
    });

    expect(screen.getByTestId('login-status')).toHaveTextContent('Logged in: true')
    expect(localStorage.getItem("isLoggedIn")).toBe("true");
  })

  test("logOut sets the state and updates localStorage", () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    act(() => {
      screen.getByRole('button', {name: /log in/i}).click();
      screen.getByRole('button', {name: /log out/i}).click();
    });

    expect(screen.getByText(/logged in: false/i)).toBeInTheDocument();
    expect(localStorage.getItem("isLoggedIn")).toBe("false");
    
  })
})