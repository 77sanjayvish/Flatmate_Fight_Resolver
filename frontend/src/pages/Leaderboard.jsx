import { useState, useEffect } from 'react';
import { leaderboardAPI } from '../services/api';
import toast from 'react-hot-toast';
import { HiStar } from 'react-icons/hi';

export default function Leaderboard() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchLeaderboard() {
      try {
        const res = await leaderboardAPI.get();
        setUsers(res.data);
      } catch {
        toast.error('Failed to load leaderboard');
      } finally {
        setLoading(false);
      }
    }
    fetchLeaderboard();
  }, []);

  const getMedalEmoji = (index) => {
    if (index === 0) return '🥇';
    if (index === 1) return '🥈';
    if (index === 2) return '🥉';
    return null;
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600" />
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-2xl sm:text-3xl font-bold text-gray-900">
          Leaderboard
        </h1>
        <p className="text-gray-600 mt-1">
          Karma points ranking — be the best flatmate!
        </p>
      </div>

      {users.length === 0 ? (
        <div className="bg-white rounded-xl shadow-md p-12 text-center">
          <span className="text-6xl">🏆</span>
          <h2 className="text-xl font-semibold text-gray-900 mt-4">
            No users yet
          </h2>
          <p className="text-gray-500 mt-2">
            Sign up to start earning karma points!
          </p>
        </div>
      ) : (
        <div className="bg-white rounded-xl shadow-md overflow-hidden">
          <div className="hidden sm:grid grid-cols-12 gap-4 px-6 py-3 bg-gray-50 text-sm font-medium text-gray-500 uppercase tracking-wider">
            <div className="col-span-1">Rank</div>
            <div className="col-span-5">User</div>
            <div className="col-span-3">Flat Code</div>
            <div className="col-span-3 text-right">Karma Points</div>
          </div>

          <div className="divide-y divide-gray-100">
            {users.map((user, index) => (
              <div
                key={user.id}
                className={`grid grid-cols-12 gap-4 px-6 py-4 items-center hover:bg-gray-50 transition ${
                  index < 3 ? 'bg-gradient-to-r from-yellow-50/50 to-transparent' : ''
                }`}
              >
                <div className="col-span-2 sm:col-span-1 text-lg font-bold text-gray-900">
                  {getMedalEmoji(index) || `#${index + 1}`}
                </div>

                <div className="col-span-10 sm:col-span-5">
                  <p className="font-medium text-gray-900">{user.userName}</p>
                  <p className="text-sm text-gray-500">{user.email}</p>
                </div>

                <div className="col-span-6 sm:col-span-3 text-sm text-gray-600">
                  <span className="sm:hidden text-gray-400">Flat: </span>
                  {user.flatCode}
                </div>

                <div className="col-span-6 sm:col-span-3 text-right">
                  <span className="inline-flex items-center space-x-1 text-lg font-bold text-indigo-600">
                    <HiStar className="text-yellow-400" />
                    <span>{user.kPoints}</span>
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
