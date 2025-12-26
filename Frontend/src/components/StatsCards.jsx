import React from 'react';
import { Activity, CheckCircle, XCircle } from 'lucide-react';
import { Card, Row, Col } from 'react-bootstrap';

const StatsCards = ({ stats }) => {
  const cards = [
    {
      title: 'Total Requests',
      value: stats.total,
      icon: Activity,
      variant: 'primary',
      bgClass: 'bg-primary bg-opacity-10',
      textClass: 'text-primary'
    },
    {
      title: 'Accepted',
      value: stats.accepted,
      icon: CheckCircle,
      variant: 'success',
      bgClass: 'bg-success bg-opacity-10',
      textClass: 'text-success'
    },
    {
      title: 'Denied',
      value: stats.denied,
      icon: XCircle,
      variant: 'danger',
      bgClass: 'bg-danger bg-opacity-10',
      textClass: 'text-danger'
    }
  ];

  const successRate = stats.total > 0 
    ? ((stats.accepted / stats.total) * 100).toFixed(1) 
    : 0;

  return (
    <Row className="g-3 mb-4">
      {cards.map((card) => {
        const Icon = card.icon;
        
        return (
          <Col key={card.title} xs={12} md={4}>
            <Card className={`stats-card ${card.bgClass} border-${card.variant}`}>
              <Card.Body>
                <div className="d-flex align-items-center justify-content-between mb-3">
                  <h6 className="text-muted mb-0">{card.title}</h6>
                  <Icon className={card.textClass} size={20} />
                </div>
                <div className={`stats-card-value ${card.textClass}`}>
                  {card.value.toLocaleString()}
                </div>
                {card.title === 'Accepted' && stats.total > 0 && (
                  <small className="text-muted">
                    Success rate: {successRate}%
                  </small>
                )}
              </Card.Body>
            </Card>
          </Col>
        );
      })}
    </Row>
  );
};

export default StatsCards;
