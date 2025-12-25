import React from 'react';
import { Play, Square, RotateCcw } from 'lucide-react';
import { Button } from 'react-bootstrap';

const ControlPanel = ({ isSimulating, onStart, onStop, onReset }) => {
  return (
    <div className="control-panel mb-4">
      <h5 className="mb-3">Simulation Controls</h5>
      <div className="d-flex gap-3 flex-wrap">
        <Button
          variant="success"
          onClick={onStart}
          disabled={isSimulating}
          className="btn-control"
        >
          <Play size={18} />
          Start Simulation
        </Button>

        <Button
          variant="danger"
          onClick={onStop}
          disabled={!isSimulating}
          className="btn-control"
        >
          <Square size={18} />
          Stop Simulation
        </Button>

        <Button
          variant="primary"
          onClick={onReset}
          className="btn-control"
        >
          <RotateCcw size={18} />
          Reset Stats
        </Button>
      </div>
    </div>
  );
};

export default ControlPanel;
