import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { complaintsAPI, votesAPI } from '../services/api';
import toast from 'react-hot-toast';
import { HiThumbUp, HiThumbDown, HiArrowLeft, HiTrash } from 'react-icons/hi';

export default function ComplaintDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [complaint, setComplaint] = useState(null);
  const [loading, setLoading] = useState(true);
  const [voteForm, setVoteForm] = useState({ votedById: '', upVote: true });
  const [voting, setVoting] = useState(false);

  useEffect(() => {
    async function fetchComplaint() {
      try {
        const res = await complaintsAPI.getById(id);
        setComplaint(res.data);
      } catch {
        toast.error('Complaint not found');
        navigate('/complaints');
      } finally {
        setLoading(false);
      }
    }
    fetchComplaint();
  }, [id, navigate]);

  const handleVote = async (isUpVote) => {
    if (!voteForm.votedById) {
      toast.error('Please enter your User ID to vote');
      return;
    }
    setVoting(true);
    try {
      await votesAPI.create({
        upVote: isUpVote,
        votedById: parseInt(voteForm.votedById),
        complaintId: parseInt(id),
      });
      toast.success(isUpVote ? 'Upvoted!' : 'Downvoted!');
      const res = await complaintsAPI.getById(id);
      setComplaint(res.data);
    } catch {
      toast.error('Failed to vote');
    } finally {
      setVoting(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('Are you sure you want to delete this complaint?'))
      return;
    try {
      await complaintsAPI.delete(id);
      toast.success('Complaint deleted');
      navigate('/complaints');
    } catch {
      toast.error('Failed to delete complaint');
    }
  };

  const severityColors = {
    MINOR: 'bg-green-100 text-green-800 border-green-200',
    ANNOYING: 'bg-yellow-100 text-yellow-800 border-yellow-200',
    MAJOR: 'bg-orange-100 text-orange-800 border-orange-200',
    NUCLEAR: 'bg-red-100 text-red-800 border-red-200',
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600" />
      </div>
    );
  }

  if (!complaint) return null;

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <button
        onClick={() => navigate('/complaints')}
        className="flex items-center space-x-2 text-gray-600 hover:text-gray-900 mb-6"
      >
        <HiArrowLeft />
        <span>Back to complaints</span>
      </button>

      <div className="bg-white rounded-xl shadow-md p-6 sm:p-8">
        <div className="flex items-start justify-between mb-4">
          <h1 className="text-2xl font-bold text-gray-900">
            {complaint.title}
          </h1>
          <button
            onClick={handleDelete}
            className="text-red-500 hover:text-red-700 p-2"
            title="Delete complaint"
          >
            <HiTrash size={20} />
          </button>
        </div>

        <div className="flex flex-wrap gap-2 mb-4">
          {complaint.complainType && (
            <span className="px-3 py-1 rounded-full text-sm font-medium bg-indigo-100 text-indigo-800">
              {complaint.complainType}
            </span>
          )}
          {complaint.severityLevel && (
            <span
              className={`px-3 py-1 rounded-full text-sm font-medium border ${
                severityColors[complaint.severityLevel] ||
                'bg-gray-100 text-gray-800'
              }`}
            >
              {complaint.severityLevel}
            </span>
          )}
          <span
            className={`px-3 py-1 rounded-full text-sm font-medium ${
              complaint.resolved
                ? 'bg-green-100 text-green-800'
                : 'bg-red-100 text-red-800'
            }`}
          >
            {complaint.resolved ? 'Resolved' : 'Open'}
          </span>
        </div>

        <p className="text-gray-700 mb-6 leading-relaxed">
          {complaint.description}
        </p>

        {complaint.localDateTime && (
          <p className="text-sm text-gray-400 mb-6">
            Filed on{' '}
            {new Date(complaint.localDateTime).toLocaleDateString('en-US', {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
              hour: '2-digit',
              minute: '2-digit',
            })}
          </p>
        )}

        <div className="border-t border-gray-100 pt-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">Votes</h2>

          <div className="flex items-center space-x-8 mb-6">
            <div className="flex items-center space-x-2">
              <HiThumbUp className="text-green-500 text-2xl" />
              <span className="text-2xl font-bold text-gray-900">
                {complaint.upVotes}
              </span>
              <span className="text-gray-500">upvotes</span>
            </div>
            <div className="flex items-center space-x-2">
              <HiThumbDown className="text-red-500 text-2xl" />
              <span className="text-2xl font-bold text-gray-900">
                {complaint.downVotes}
              </span>
              <span className="text-gray-500">downvotes</span>
            </div>
          </div>

          <div className="bg-gray-50 rounded-lg p-4">
            <h3 className="text-sm font-medium text-gray-700 mb-3">
              Cast your vote
            </h3>
            <div className="flex flex-col sm:flex-row items-stretch sm:items-center gap-3">
              <input
                type="number"
                placeholder="Your User ID"
                value={voteForm.votedById}
                onChange={(e) =>
                  setVoteForm({ ...voteForm, votedById: e.target.value })
                }
                className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none"
              />
              <div className="flex space-x-2">
                <button
                  onClick={() => handleVote(true)}
                  disabled={voting}
                  className="flex-1 sm:flex-none flex items-center justify-center space-x-2 px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded-lg font-medium transition-colors disabled:opacity-50"
                >
                  <HiThumbUp />
                  <span>Upvote</span>
                </button>
                <button
                  onClick={() => handleVote(false)}
                  disabled={voting}
                  className="flex-1 sm:flex-none flex items-center justify-center space-x-2 px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded-lg font-medium transition-colors disabled:opacity-50"
                >
                  <HiThumbDown />
                  <span>Downvote</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
