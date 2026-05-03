import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { complaintsAPI } from '../services/api';
import toast from 'react-hot-toast';
import { HiPlus, HiThumbUp, HiThumbDown } from 'react-icons/hi';

export default function Complaints() {
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCreate, setShowCreate] = useState(false);

  const fetchComplaints = async () => {
    try {
      const res = await complaintsAPI.getAll();
      setComplaints(res.data);
    } catch {
      toast.error('Failed to load complaints');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchComplaints();
  }, []);

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this complaint?')) return;
    try {
      await complaintsAPI.delete(id);
      toast.success('Complaint deleted');
      fetchComplaints();
    } catch {
      toast.error('Failed to delete complaint');
    }
  };

  const severityColors = {
    MINOR: 'bg-green-100 text-green-800',
    ANNOYING: 'bg-yellow-100 text-yellow-800',
    MAJOR: 'bg-orange-100 text-orange-800',
    NUCLEAR: 'bg-red-100 text-red-800',
  };

  const typeColors = {
    NOISE: 'bg-purple-100 text-purple-800',
    CLEANLINESS: 'bg-blue-100 text-blue-800',
    BILLS: 'bg-emerald-100 text-emerald-800',
    PETS: 'bg-amber-100 text-amber-800',
    OTHER: 'bg-gray-100 text-gray-800',
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600" />
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold text-gray-900">
            Complaints
          </h1>
          <p className="text-gray-600 mt-1">
            {complaints.length} complaint{complaints.length !== 1 ? 's' : ''} filed
          </p>
        </div>
        <button
          onClick={() => setShowCreate(!showCreate)}
          className="flex items-center space-x-2 bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2.5 rounded-lg font-medium transition-colors"
        >
          <HiPlus />
          <span className="hidden sm:inline">New Complaint</span>
        </button>
      </div>

      {showCreate && (
        <CreateComplaint
          onClose={() => setShowCreate(false)}
          onCreated={fetchComplaints}
        />
      )}

      {complaints.length === 0 ? (
        <div className="bg-white rounded-xl shadow-md p-12 text-center">
          <span className="text-6xl">🎉</span>
          <h2 className="text-xl font-semibold text-gray-900 mt-4">
            No complaints yet!
          </h2>
          <p className="text-gray-500 mt-2">
            Everything is peaceful in your flat.
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {complaints.map((complaint) => (
            <div
              key={complaint.id}
              className="bg-white rounded-xl shadow-md p-5 hover:shadow-lg transition-shadow"
            >
              <div className="flex items-start justify-between mb-3">
                <Link
                  to={`/complaints/${complaint.id}`}
                  className="font-semibold text-gray-900 hover:text-indigo-600 transition-colors"
                >
                  {complaint.title}
                </Link>
                {complaint.resolved && (
                  <span className="px-2 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800 shrink-0 ml-2">
                    Resolved
                  </span>
                )}
              </div>

              <p className="text-sm text-gray-600 mb-3 line-clamp-2">
                {complaint.description}
              </p>

              <div className="flex flex-wrap gap-2 mb-3">
                {complaint.complainType && (
                  <span
                    className={`px-2 py-0.5 rounded-full text-xs font-medium ${
                      typeColors[complaint.complainType] || typeColors.OTHER
                    }`}
                  >
                    {complaint.complainType}
                  </span>
                )}
                {complaint.severityLevel && (
                  <span
                    className={`px-2 py-0.5 rounded-full text-xs font-medium ${
                      severityColors[complaint.severityLevel] ||
                      'bg-gray-100 text-gray-800'
                    }`}
                  >
                    {complaint.severityLevel}
                  </span>
                )}
              </div>

              <div className="flex items-center justify-between pt-3 border-t border-gray-100">
                <div className="flex items-center space-x-4 text-sm text-gray-500">
                  <span className="flex items-center space-x-1">
                    <HiThumbUp className="text-green-500" />
                    <span>{complaint.upVotes}</span>
                  </span>
                  <span className="flex items-center space-x-1">
                    <HiThumbDown className="text-red-500" />
                    <span>{complaint.downVotes}</span>
                  </span>
                </div>
                <button
                  onClick={() => handleDelete(complaint.id)}
                  className="text-red-500 hover:text-red-700 text-sm font-medium"
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

function CreateComplaint({ onClose, onCreated }) {
  const [form, setForm] = useState({
    title: '',
    description: '',
    complainType: 'NOISE',
    severityLevel: 'MINOR',
    filedByUserId: '',
  });
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await complaintsAPI.create({
        ...form,
        filedByUserId: parseInt(form.filedByUserId),
        resolved: false,
        localDateTime: new Date().toISOString(),
        upVotes: 0,
        downVotes: 0,
      });
      toast.success('Complaint filed!');
      onClose();
      onCreated();
    } catch {
      toast.error('Failed to create complaint');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-xl shadow-md p-6 mb-6">
      <h2 className="text-lg font-semibold text-gray-900 mb-4">
        File a New Complaint
      </h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Title
            </label>
            <input
              type="text"
              required
              value={form.title}
              onChange={(e) => setForm({ ...form, title: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none"
              placeholder="What's the issue?"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Your User ID
            </label>
            <input
              type="number"
              required
              value={form.filedByUserId}
              onChange={(e) =>
                setForm({ ...form, filedByUserId: e.target.value })
              }
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none"
              placeholder="1"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">
            Description
          </label>
          <textarea
            required
            rows={3}
            value={form.description}
            onChange={(e) =>
              setForm({ ...form, description: e.target.value })
            }
            className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none resize-none"
            placeholder="Describe the situation..."
          />
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Type
            </label>
            <select
              value={form.complainType}
              onChange={(e) =>
                setForm({ ...form, complainType: e.target.value })
              }
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none"
            >
              <option value="NOISE">Noise</option>
              <option value="CLEANLINESS">Cleanliness</option>
              <option value="BILLS">Bills</option>
              <option value="PETS">Pets</option>
              <option value="OTHER">Other</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Severity
            </label>
            <select
              value={form.severityLevel}
              onChange={(e) =>
                setForm({ ...form, severityLevel: e.target.value })
              }
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none"
            >
              <option value="MINOR">Minor</option>
              <option value="ANNOYING">Annoying</option>
              <option value="MAJOR">Major</option>
              <option value="NUCLEAR">Nuclear</option>
            </select>
          </div>
        </div>

        <div className="flex justify-end space-x-3">
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 text-gray-700 hover:text-gray-900 font-medium"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={loading}
            className="px-6 py-2 bg-indigo-600 hover:bg-indigo-700 text-white font-medium rounded-lg transition-colors disabled:opacity-50"
          >
            {loading ? 'Filing...' : 'File Complaint'}
          </button>
        </div>
      </form>
    </div>
  );
}
