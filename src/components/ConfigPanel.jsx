import React from 'react';
import { Settings } from 'lucide-react';
import { Form } from 'react-bootstrap';

const ConfigPanel = ({ config, setConfig, isSimulating }) => {
  const handleChange = (field, value) => {
    setConfig(prev => ({ ...prev, [field]: value }));
  };

  return (
    <div className="config-panel">
      <div className="d-flex align-items-center gap-2 mb-4">
        <Settings className="text-primary" size={24} />
        <h2 className="h4 mb-0">Configuration</h2>
      </div>

      <Form>
        {/* Client ID */}
        <Form.Group className="mb-3">
          <Form.Label>Client ID</Form.Label>
          <Form.Control
            type="text"
            value={config.clientId}
            onChange={(e) => handleChange('clientId', e.target.value)}
            disabled={isSimulating}
            placeholder="user_001"
          />
        </Form.Group>

        {/* Algorithm */}
        <Form.Group className="mb-3">
          <Form.Label>Algorithm</Form.Label>
          <Form.Select
            value={config.algorithm}
            onChange={(e) => handleChange('algorithm', e.target.value)}
            disabled={isSimulating}
          >
            <option value="TokenBucket">Token Bucket</option>
            <option value="LeakyBucket">Leaky Bucket</option>
            <option value="FixedWindow">Fixed Window</option>
          </Form.Select>
        </Form.Group>

        {/* Capacity */}
        <Form.Group className="mb-3">
          <Form.Label>Capacity</Form.Label>
          <Form.Control
            type="number"
            value={config.capacity}
            onChange={(e) => handleChange('capacity', parseInt(e.target.value) || 0)}
            disabled={isSimulating}
            min="1"
            max="100"
          />
          <Form.Text className="text-muted">
            Burst capacity or queue size
          </Form.Text>
        </Form.Group>

        {/* Refill/Leak Rate */}
        <Form.Group className="mb-3">
          <Form.Label>Refill/Leak Rate (per second)</Form.Label>
          <Form.Control
            type="number"
            value={config.rate}
            onChange={(e) => handleChange('rate', parseInt(e.target.value) || 0)}
            disabled={isSimulating}
            min="1"
            max="50"
          />
          <Form.Text className="text-muted">
            Tokens/requests per second
          </Form.Text>
        </Form.Group>

        {/* Simulation Speed */}
        <Form.Group className="mb-3">
          <Form.Label>Simulation Speed: {config.speed} req/sec</Form.Label>
          <Form.Range
            value={config.speed}
            onChange={(e) => handleChange('speed', parseInt(e.target.value))}
            disabled={isSimulating}
            min="1"
            max="20"
          />
          <div className="d-flex justify-content-between">
            <small className="text-muted">1</small>
            <small className="text-muted">20</small>
          </div>
        </Form.Group>

        {/* Info Card */}
        <div className="info-box mt-4">
          <strong>Tip:</strong> Start the simulation to send requests at the configured speed. 
          The chart updates every second with aggregated results.
        </div>
      </Form>
    </div>
  );
};

export default ConfigPanel;
