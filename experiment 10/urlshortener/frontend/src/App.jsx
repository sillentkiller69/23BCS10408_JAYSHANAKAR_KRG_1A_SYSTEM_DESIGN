import { useState } from 'react'
import './App.css'

function App() {
  const [formData, setFormData] = useState({
    orignalurl: '',
    customName: '',
    expiryDate: ''
  })
  const [result, setResult] = useState(null)
  const [loading, setLoading] = useState(false)
  const [statsUrl, setStatsUrl] = useState('')
  const [stats, setStats] = useState(null)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    try {
      const response = await fetch('http://localhost:8080/addurl', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
      const data = await response.json()
      setResult(data)
    } catch (error) {
      console.error('Error:', error)
      setResult({ error: 'Failed to shorten URL' })
    }
    setLoading(false)
  }

  const getStats = async () => {
    if (!statsUrl) return
    try {
      const response = await fetch(`http://localhost:8080/count/${statsUrl}`)
      const data = await response.json()
      setStats(data)
    } catch (error) {
      console.error('Error:', error)
      setStats({ error: 'Failed to get stats' })
    }
  }

  return (
    <div className="app">
      <h1>URL Shortener</h1>
      
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="orignalurl">Original URL *</label>
          <input
            type="url"
            id="orignalurl"
            name="orignalurl"
            value={formData.orignalurl}
            onChange={handleChange}
            required
            placeholder="https://example.com"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="customName">Custom Name (optional)</label>
          <input
            type="text"
            id="customName"
            name="customName"
            value={formData.customName}
            onChange={handleChange}
            placeholder="my-link"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="expiryDate">Expiry Date (optional)</label>
          <input
            type="datetime-local"
            id="expiryDate"
            name="expiryDate"
            value={formData.expiryDate}
            onChange={handleChange}
          />
        </div>
        
        <button type="submit" disabled={loading}>
          {loading ? 'Shortening...' : 'Shorten URL'}
        </button>
      </form>

      {result && (
        <div className="result">
          <h2>Shortened URL</h2>
          {result.error ? (
            <p className="error">{result.error}</p>
          ) : (
            <div>
              <p><strong>Short URL:</strong> <a href={`http://localhost:8080/${result.shortUrl}`} target="_blank" rel="noopener noreferrer">{result.shortUrl}</a></p>
              <p><strong>Original URL:</strong> {result.orignalUrl}</p>
              <p><strong>Expiry Date:</strong> {result.expiryDate}</p>
              <p><strong>Clicks:</strong> {result.clickCount}</p>
            </div>
          )}
        </div>
      )}

      <div className="stats-section">
        <h2>Get Click Count</h2>
        <div className="stats-form">
          <input
            type="text"
            value={statsUrl}
            onChange={(e) => setStatsUrl(e.target.value)}
            placeholder="Enter short URL"
          />
          <button onClick={getStats}>Get Count</button>
        </div>
        {stats && (
          <p><strong>Clicks:</strong> {stats.error ? stats.error : stats}</p>
        )}
      </div>
    </div>
  )
}

export default App

      