import  { useAuth } from "./AuthContext";
import { describe, expect, test } from "vitest";
import { AuthProvider } from "./AuthProvider";
import { render, screen, act } from "@testing-library/react";

function TestComponent() {
  const {isLoggedIn, logIn} = useAuth();

  return (
    <div>
      <p data-testid="login-status">Logged in: {isLoggedIn.toString()}</p>
      <button onClick={logIn}>Log in</button>
    </div>
  )
}

describe('AuthProvider', () => {
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
  })
})