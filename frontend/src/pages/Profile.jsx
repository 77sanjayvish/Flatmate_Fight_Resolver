import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { usersAPI } from '../services/api';
import toast from 'react-hot-toast';
import { HiUser, HiMail, HiHome, HiStar, HiShieldCheck } from 'react-icons/hi';

export default function Profile() {
  const { user: authUser } = useAuth();
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchUsers() {
      try {
        const res = await usersAPI.getAll();
        setUsers(res.data);
      } catch {
        // may fail if not admin
      } finally {
        setLoading(false);
      }
    }
    fetchUsers();
  }, []);

  const currentUser = users.find(
    (u) => u.email === authUser?.email || u.userName === authUser?.userName
  );

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600" />
      </div>
    );
  }

  const displayUser = currentUser || authUser;

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-2xl sm:text-3xl font-bold text-gray-900">
          Profile
        </h1>
        <p className="text-gray-600 mt-1">Your account information</p>
      </div>

      <div className="bg-white rounded-xl shadow-md overflow-hidden">
        <div className="bg-gradient-to-r from-indigo-500 to-purple-500 h-32 sm:h-40" />

        <div className="px-6 sm:px-8 pb-8">
          <div className="-mt-12 sm:-mt-16 mb-6">
            <div className="w-24 h-24 sm:w-32 sm:h-32 bg-white rounded-full border-4 border-white shadow-lg flex items-center justify-center">
              <span className="text-4xl sm:text-5xl">
                {displayUser?.userName?.[0]?.toUpperCase() ||
                  displayUser?.email?.[0]?.toUpperCase() ||
                  '?'}
              </span>
            </div>
          </div>

          <div className="space-y-4">
            <div className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
              <HiUser className="text-indigo-500 text-xl shrink-0" />
              <div>
                <p className="text-xs text-gray-500 uppercase tracking-wide">
                  Username
                </p>
                <p className="font-medium text-gray-900">
                  {displayUser?.userName || 'N/A'}
                </p>
              </div>
            </div>

            <div className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
              <HiMail className="text-indigo-500 text-xl shrink-0" />
              <div>
                <p className="text-xs text-gray-500 uppercase tracking-wide">
                  Email
                </p>
                <p className="font-medium text-gray-900">
                  {displayUser?.email || 'N/A'}
                </p>
              </div>
            </div>

            {currentUser?.flatCode && (
              <div className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                <HiHome className="text-indigo-500 text-xl shrink-0" />
                <div>
                  <p className="text-xs text-gray-500 uppercase tracking-wide">
                    Flat Code
                  </p>
                  <p className="font-medium text-gray-900">
                    {currentUser.flatCode}
                  </p>
                </div>
              </div>
            )}

            {currentUser && (
              <div className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                <HiStar className="text-yellow-400 text-xl shrink-0" />
                <div>
                  <p className="text-xs text-gray-500 uppercase tracking-wide">
                    Karma Points
                  </p>
                  <p className="font-medium text-gray-900 text-xl">
                    {currentUser.kPoints}
                  </p>
                </div>
              </div>
            )}

            {currentUser?.role && (
              <div className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                <HiShieldCheck className="text-indigo-500 text-xl shrink-0" />
                <div>
                  <p className="text-xs text-gray-500 uppercase tracking-wide">
                    Role
                  </p>
                  <p className="font-medium text-gray-900">
                    {currentUser.role}
                  </p>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>

      {users.length > 1 && (
        <div className="mt-8 bg-white rounded-xl shadow-md p-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">
            All Flatmates
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            {users.map((u) => (
              <div
                key={u.id}
                className="flex items-center space-x-3 p-3 border border-gray-100 rounded-lg"
              >
                <div className="w-10 h-10 bg-indigo-100 rounded-full flex items-center justify-center text-indigo-600 font-medium">
                  {u.userName?.[0]?.toUpperCase() || '?'}
                </div>
                <div className="min-w-0">
                  <p className="font-medium text-gray-900 truncate">
                    {u.userName}
                  </p>
                  <p className="text-sm text-gray-500 truncate">{u.email}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
