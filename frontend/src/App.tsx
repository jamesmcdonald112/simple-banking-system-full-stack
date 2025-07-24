
import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [message, setMessage] = useState('');


  useEffect(() => {
    fetch("http://localhost:8080/api/test")
      .then(res => res.text())
      .then(text => setMessage(text))
  },[])

  return (
    <>
    <h1 className="text-3xl font-bold underline">
      {message}
    </h1>
     
    </>
  )
}

export default App
