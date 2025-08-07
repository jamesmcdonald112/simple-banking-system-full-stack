import { Route, Routes } from 'react-router-dom'
import './App.css'
import Dashboard from './pages/Dashboard'
import Login from './pages/Login'
import CreateAccount from './pages/CreateAccount'
import NotFound from './pages/NotFound'
import PrivateRoute from './routes/PrivateRoute'
import { Layout } from './components/Layout'
import AccountCreated from './pages/AccountCreated'

function App() {

  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path='/login' element={<Login />}/>
        <Route path='/create-account' element={<CreateAccount />}/>
        <Route path='/account-created' element={<AccountCreated />}/>

        <Route element={<PrivateRoute />}>
          <Route index element={<Dashboard />} />
          <Route path='/dashboard' element={<Dashboard />}/>
        </Route>

        <Route path='*' element={<NotFound />}/>
      </Route>
    </Routes>
  )
}

export default App
