import { Route, Routes } from 'react-router-dom'
import './App.css'
import Dashboard from './pages/Dashboard'
import Login from './pages/Login'
import CreateAccount from './pages/CreateAccount'
import NotFound from './pages/NotFound'
import PrivateRoute from './routes/PrivateRoute'
import { Layout } from './components/Layout'
import AccountCreated from './pages/AccountCreated'
import AuthGate from './routes/AuthGate'
import PublicRoute from './routes/PublicRoute'

function App() {

  return (
    <Routes>
      <Route element={<Layout />}>
        {/* Root decides based on auth */}
        <Route index element={<AuthGate />} />

        {/* Public pages but bounce if logged in */}
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/create-account" element={<CreateAccount />} />
          <Route path="/account-created" element={<AccountCreated />} />
        </Route>

        {/* Private Pages */}
        <Route element={<PrivateRoute />}>
          <Route element={<Dashboard />} />
          <Route path='/dashboard' element={<Dashboard />}/>
        </Route>

        <Route path='*' element={<NotFound />}/>
      </Route>
    </Routes>
  )
}

export default App
