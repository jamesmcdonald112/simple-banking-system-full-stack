import { render, screen } from '@testing-library/react'
import { test, expect } from 'vitest'
import App from './App'
import Login from './pages/Login'
import CreateAccount from './pages/CreateAccount'
import NotFound from './pages/NotFound'
import { type ReactNode } from 'react'
import { AuthContext } from './context/AuthContext'
import { MemoryRouter } from 'react-router-dom'
import type { Account } from './types/Account'



test('renders Login page when visiting /login', () => {
  render(
    <MemoryRouter initialEntries={['/login']}>
      <MockAuthProvider isLoggedInStatus={false}>
        <Login />
      </MockAuthProvider>
    </MemoryRouter>
  )
  expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument()
})

test('renders Dashboard page when visiting /dashboard (user must be signed in)', () => {
  render(
    <MemoryRouter initialEntries={['/dashboard']}>
      <MockAuthProvider isLoggedInStatus={true}>
        <App />
      </MockAuthProvider>
    </MemoryRouter>
  )
  expect(screen.getByText(/welcome back/i)).toBeInTheDocument()
})

test('renders Create Account page when visiting /create-account', () => {
  render(
    <MemoryRouter initialEntries={['/create-account']}>
      <CreateAccount />
    </MemoryRouter>
  )
  expect(screen.getByRole('heading', { name: /create account/i })).toBeInTheDocument()
})

test('renders Not Found page when visiting any unrouted paths', () => {
  render(
    <MemoryRouter initialEntries={['/asdfghjkl']}>
      <NotFound />
    </MemoryRouter>
  )
  expect(screen.getByRole('heading', { name: /not found/i })).toBeInTheDocument()
})


test('renders login page when the user is signed out', () => {
  render(
    <MemoryRouter initialEntries={['/']}>
      <MockAuthProvider isLoggedInStatus={false}>
        <App />
      </MockAuthProvider>
    </MemoryRouter>
  )
  expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument()
})

test('renders dashboard page when the user is signed in', () => {
  render(
    <MemoryRouter initialEntries={['/']}>
      <MockAuthProvider isLoggedInStatus={true}>
        <App />
      </MockAuthProvider>
    </MemoryRouter>
  )
  expect(screen.getByText(/welcome back/i)).toBeInTheDocument()
})

type MockAuthProviderProps = {
  children: ReactNode;
  isLoggedInStatus: boolean;
  accountOverride?: Partial<Account>;
}

function MockAuthProvider({ children, isLoggedInStatus, accountOverride }: MockAuthProviderProps) {
  const fakeAccount: Account | null = isLoggedInStatus
    ? {
        id: 1,
        name: 'Test User',
        email: 'test@example.com',
        phone: '+353123456789',
        cardNumber: '4000123412341234',
        balance: 100,
        ...accountOverride,
      }
    : null;

  const logIn = (_acct: Account) => {};
  const logOut = () => {};

  return (
    <AuthContext.Provider value={{ account: fakeAccount, isLoggedIn: !!fakeAccount, logIn, logOut }}>
      {children}
    </AuthContext.Provider>
  );
}