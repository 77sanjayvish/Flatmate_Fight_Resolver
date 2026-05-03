import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { statsAPI, complaintsAPI } from '../services/api';
import { HiClipboardList, HiCheckCircle, HiExclamationCircle } from 'react-icons/hi';

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [recentComplaints, setRecentComplaints] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const [statsRes, complaintsRes] = await Promise.all([
          statsAPI.get(),
          complaintsAPI.getAll(),
        ]);
        setStats(statsRes.data);
        setRecentComplaints(complaintsRes.data.slice(0, 5));
      } catch {
        // silently handle errors
      } finally {
        setLoading(false);
      }
    }
    fetchData();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600" />
      </div>
    );
  }

  const statCards = [
    {
      label: 'Total Complaints',
      value: stats?.totalComplaints ?? 0,
      icon: HiClipboardList,
      color: 'bg-blue-500',
    },
    {
      label: 'Resolved',
      value: stats?.resolvedComplaints ?? 0,
      icon: HiCheckCircle,
      color: 'bg-green-500',
    },
    {
      label: 'Open',
      value: stats?.openComplaints ?? 0,
      icon: HiExclamationCircle,
      color: 'bg-orange-500',
    },
  ];

  const severityColors = {
    MINOR: 'bg-green-100 text-green-800',
    ANNOYING: 'bg-yellow-100 text-yellow-800',
    MAJOR: 'bg-orange-100 text-orange-800',
    NUCLEAR: 'bg-red-100 text-red-800',
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-2xl sm:text-3xl font-bold text-gray-900">
          Dashboard
        </h1>
        <p className="text-gray-600 mt-1">Overview of your flat&apos;s complaint status</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 sm:gap-6 mb-8">
        {statCards.map((card) => (
          <div
            key={card.label}
            className="bg-white rounded-xl shadow-md p-6 flex items-center space-x-4"
          >
            <div className={`${card.color} p-3 rounded-lg`}>
              <card.icon className="text-white text-2xl" />
            </div>
            <div>
              <p className="text-sm text-gray-500">{card.label}</p>
              <p className="text-2xl font-bold text-gray-900">{card.value}</p>
            </div>
          </div>
        ))}
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-semibold text-gray-900">
            Recent Complaints
          </h2>
          <Link
            to="/complaints"
            className="text-indigo-600 hover:text-indigo-700 text-sm font-medium"
          >
            View all →
          </Link>
        </div>

        {recentComplaints.length === 0 ? (
          <p className="text-gray-500 text-center py-8">
            No complaints yet. Your flat is peaceful! 🎉
          </p>
        ) : (
          <div className="space-y-3">
            {recentComplaints.map((complaint) => (
              <Link
                key={complaint.id}
                to={`/complaints/${complaint.id}`}
                className="block p-4 border border-gray-100 rounded-lg hover:bg-gray-50 transition"
              >
                <div className="flex items-center justify-between">
                  <div className="flex-1 min-w-0">
                    <h3 className="font-medium text-gray-900 truncate">
                      {complaint.title}
                    </h3>
                    <p className="text-sm text-gray-500 mt-1 truncate">
                      {complaint.description}
                    </p>
                  </div>
                  <div className="flex items-center space-x-2 ml-4">
                    <span
                      className={`px-2 py-1 rounded-full text-xs font-medium ${
                        severityColors[complaint.severityLevel] ||
                        'bg-gray-100 text-gray-800'
                      }`}
                    >
                      {complaint.severityLevel}
                    </span>
                    {complaint.resolved && (
                      <span className="px-2 py-1 rounded-full text-xs font-medium bg-green-100 text-green-800">
                        Resolved
                      </span>
                    )}
                  </div>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
