import React, { useState, useEffect, useRef } from 'react';
import { Container, Row, Col, Alert } from 'react-bootstrap';
import ConfigPanel from './components/ConfigPanel';
import StatsCards from './components/StatsCards';
import TrafficChart from './components/TrafficChart';
import ControlPanel from './components/ControlPanel';
import { sendRateLimitRequest } from './services/api';
import { AlertCircle } from 'lucide-react';

function App() {
  // Configuration state
  const [config, setConfig] = useState({
    clientId: 'user_001',
    algorithm: 'TokenBucket',
    capacity: 10,
    rate: 5,
    speed: 10 // requests per second
  });

  // Simulation state
  const [isSimulating, setIsSimulating] = useState(false);
  const [stats, setStats] = useState({
    total: 0,
    accepted: 0,
    denied: 0
  });

  // Chart data state
  const [chartData, setChartData] = useState({
    labels: [],
    accepted: [],
    denied: []
  });

  // Error state
  const [error, setError] = useState(null);

  // Refs for intervals and aggregation
  const requestIntervalRef = useRef(null);
  const chartUpdateIntervalRef = useRef(null);
  const currentSecondDataRef = useRef({ accepted: 0, denied: 0 });

  // Format time for chart labels
  const formatTime = () => {
    const now = new Date();
    return now.toLocaleTimeString('en-US', { hour12: false });
  };

  // Send a single request to the backend
  const sendRequest = async () => {
    try {
      const result = await sendRateLimitRequest(config);
      
      // Update aggregation counters
      if (result.allowed) {
        currentSecondDataRef.current.accepted++;
      } else {
        currentSecondDataRef.current.denied++;
      }

      // Update total stats
      setStats(prev => ({
        total: prev.total + 1,
        accepted: prev.accepted + (result.allowed ? 1 : 0),
        denied: prev.denied + (result.allowed ? 0 : 1)
      }));

      setError(null);
    } catch (err) {
      console.error('Request failed:', err);
      setError('Failed to connect to backend. Is the server running on http://localhost:8080?');
    }
  };

  // Update chart with aggregated data every second
  const updateChart = () => {
    const time = formatTime();
    const { accepted, denied } = currentSecondDataRef.current;

    setChartData(prev => {
      const newLabels = [...prev.labels, time];
      const newAccepted = [...prev.accepted, accepted];
      const newDenied = [...prev.denied, denied];

      // Keep only last 30 data points
      const maxPoints = 30;
      if (newLabels.length > maxPoints) {
        newLabels.shift();
        newAccepted.shift();
        newDenied.shift();
      }

      return {
        labels: newLabels,
        accepted: newAccepted,
        denied: newDenied
      };
    });

    // Reset counters for next second
    currentSecondDataRef.current = { accepted: 0, denied: 0 };
  };

  // Start simulation
  const handleStart = () => {
    if (isSimulating) return;

    setIsSimulating(true);
    setError(null);

    // Calculate interval based on speed (requests per second)
    const requestInterval = 1000 / config.speed;

    // Start sending requests
    requestIntervalRef.current = setInterval(() => {
      sendRequest();
    }, requestInterval);

    // Start chart updates every second
    chartUpdateIntervalRef.current = setInterval(() => {
      updateChart();
    }, 1000);
  };

  // Stop simulation
  const handleStop = () => {
    setIsSimulating(false);

    if (requestIntervalRef.current) {
      clearInterval(requestIntervalRef.current);
      requestIntervalRef.current = null;
    }

    if (chartUpdateIntervalRef.current) {
      clearInterval(chartUpdateIntervalRef.current);
      chartUpdateIntervalRef.current = null;
    }

    // Push any remaining data to chart
    if (currentSecondDataRef.current.accepted > 0 || currentSecondDataRef.current.denied > 0) {
      updateChart();
    }
  };

  // Reset all stats and chart
  const handleReset = () => {
    handleStop();
    setStats({ total: 0, accepted: 0, denied: 0 });
    setChartData({ labels: [], accepted: [], denied: [] });
    currentSecondDataRef.current = { accepted: 0, denied: 0 };
    setError(null);
  };

  // Cleanup on unmount
  useEffect(() => {
    return () => {
      if (requestIntervalRef.current) {
        clearInterval(requestIntervalRef.current);
      }
      if (chartUpdateIntervalRef.current) {
        clearInterval(chartUpdateIntervalRef.current);
      }
    };
  }, []);

  return (
    <div className="min-vh-100">
      {/* Header */}
      <header className="app-header">
        <Container>
          <h1 className="h3 mb-1">Rate Limiter Traffic Simulator</h1>
          <p className="text-muted mb-0 small">
            Real-time visualization and testing dashboard
          </p>
        </Container>
      </header>

      {/* Main Content */}
      <Container className="py-4">
        <Row className="g-4">
          {/* Left Sidebar - Configuration */}
          <Col xs={12} lg={3}>
            <ConfigPanel 
              config={config} 
              setConfig={setConfig} 
              isSimulating={isSimulating}
            />
          </Col>

          {/* Right Main Area - Stats and Chart */}
          <Col xs={12} lg={9}>
            {/* Error Alert */}
            {error && (
              <Alert variant="danger" className="d-flex align-items-center gap-2 mb-3">
                <AlertCircle size={20} />
                <span>{error}</span>
              </Alert>
            )}

            {/* Control Panel */}
            <ControlPanel
              isSimulating={isSimulating}
              onStart={handleStart}
              onStop={handleStop}
              onReset={handleReset}
            />

            {/* Stats Cards */}
            <StatsCards stats={stats} />

            {/* Traffic Chart */}
            <TrafficChart chartData={chartData} />
          </Col>
        </Row>
      </Container>

      {/* Footer */}
      <footer className="app-footer">
        <Container>
          <p className="mb-0">Built with React, Chart.js, and Bootstrap</p>
        </Container>
      </footer>
    </div>
  );
}

export default App;
